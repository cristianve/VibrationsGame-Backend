package com.vibration.backend.infrastructure.controller;

import com.google.gson.Gson;
import com.vibration.backend.application.dto.*;
import com.vibration.backend.application.services.*;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.exceptions.SessionException;
import com.vibration.backend.domain.model.Figures;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.SocketAction;
import com.vibration.backend.infrastructure.victory.VictoryManager;
import com.vibration.backend.infrastructure.ws.WebSocketAction;
import com.vibration.backend.infrastructure.ws.WebSocketAttributes;
import com.vibration.backend.infrastructure.ws.WebSocketOperations;
import com.vibration.backend.infrastructure.ws.WebSocketState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;

@Controller
public class PlayController extends TextWebSocketHandler {

    private static final Logger log = LogManager.getLogger(PlayController.class);

    private final Connect connect;
    private final Disconnect disconnect;
    private final CreateGame createGame;
    private final JoinGame joinGame;
    private final StartGame startGame;
    private final DeleteCreatedGame deleteCreatedGame;
    private final LeaveGame leaveGame;
    private final StoreCoordinates storeCoordinates;
    private final ReviewCoordinates reviewCoordinates;
    private final CheckSocketSession checkSocketSession;
    private final CheckSocketUserAdmin checkSocketUserAdmin;
    private final UpdatePoints updatePoints;
    private final NextFigure nextFigure;
    private final VictoryManager victoryManager;

    @Autowired
    public PlayController(Connect connect,
                          Disconnect disconnect,
                          CreateGame createGame,
                          JoinGame joinGame,
                          StartGame startGame,
                          DeleteCreatedGame deleteCreatedGame,
                          LeaveGame leaveGame,
                          StoreCoordinates storeCoordinates,
                          ReviewCoordinates reviewCoordinates,
                          CheckSocketSession checkSocketSession,
                          CheckSocketUserAdmin checkSocketUserAdmin,
                          UpdatePoints updatePoints,
                          NextFigure nextFigure,
                          VictoryManager victoryManager) {
        this.connect = connect;
        this.disconnect = disconnect;
        this.createGame = createGame;
        this.joinGame = joinGame;
        this.startGame = startGame;
        this.deleteCreatedGame = deleteCreatedGame;
        this.leaveGame = leaveGame;
        this.storeCoordinates = storeCoordinates;
        this.reviewCoordinates = reviewCoordinates;
        this.checkSocketSession = checkSocketSession;
        this.checkSocketUserAdmin = checkSocketUserAdmin;
        this.updatePoints = updatePoints;
        this.nextFigure = nextFigure;
        this.victoryManager = victoryManager;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        var user = connect.execute(session);
        var responseBody = new ConnectionResponseBody(0, "SESSION OPENED",
                WebSocketState.CONNECTED.toString(), WebSocketAction.IDLE.toString(), user.getUserName());
        WebSocketOperations.sendObjectMessage(session, responseBody);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        WebSocketSession storedSession;
        try {
            storedSession = checkSocketSession.execute(session);
        } catch (SessionException e) {
            log.warn("Invalid session received due to {}", e.getMessage());

            var responseBody = new ErrorResponseBody(1, "INVALID SESSION");
            WebSocketOperations.sendObjectMessage(session, responseBody);
            return;
        }

        var socketState = WebSocketAttributes.getWebSocketState(storedSession);

        switch (socketState) {
            case CONNECTED:
                connectedState(session, storedSession, message);
                break;
            case CREATED:
                createdState(session, storedSession, message);
                break;
            case JOINED:
                joinedState(session, storedSession, message);
                break;
            case PLAYING:
                playingState(session, storedSession, message);
                break;
            case RANKING:
            case WAITING:
            case END:
                //Do nothing
                break;
            default:
                var responseBody = new ErrorResponseBody(1, "INVALID STATE");
                WebSocketOperations.sendObjectMessage(session, responseBody);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        disconnect.execute(session);
    }

    private void connectedState(WebSocketSession session, WebSocketSession storedSession, TextMessage message) {
        BasicRequestBody requestBody = new Gson().fromJson(message.getPayload(),
                BasicRequestBody.class);

        if (SocketAction.CREATE.equals(requestBody.getAction())) {
            var createGameRequestBody = new Gson().fromJson(message.getPayload(),
                    CreateGameRequestBody.class);

            GameId gameId;
            try {
                gameId = createGame.execute(storedSession, createGameRequestBody);
            } catch (GameException e) {
                log.warn("Error creating game due to {}", e.getMessage());

                var responseBody = new ErrorResponseBody(1, "GAME NOT CREATED");
                WebSocketOperations.sendObjectMessage(session, responseBody);
                return;
            }

            var responseBody = new CreateGameResponseBody(0, "GAME CREATED",
                    WebSocketState.CREATED.toString(), WebSocketAction.IDLE.toString(), gameId.getGameUuid());
            WebSocketOperations.sendObjectMessage(session, responseBody);
        }
        else if (SocketAction.JOIN.equals(requestBody.getAction())) {
            var assignUserToGameRequestBody = new Gson().fromJson(message.getPayload(),
                    AssignUserToGameRequestBody.class);

            try {
                joinGame.execute(storedSession, assignUserToGameRequestBody);
            } catch (GameException e) {
                log.warn("Error joining game due to {}", e.getMessage());

                var responseBody = new ErrorResponseBody(1, "USER NOT ADDED TO GAME");
                WebSocketOperations.sendObjectMessage(session, responseBody);
                return;
            }

            var responseBody = new JoinedResponseBody(0, "USER ADDED TO GAME",
                    WebSocketState.JOINED.toString(), WebSocketAction.IDLE.toString(),
                    assignUserToGameRequestBody.getGameId());
            WebSocketOperations.sendObjectMessage(session, responseBody);
        }
        else if (message.getPayload().contains(WebSocketAction.IDLE.toString())) {
            log.debug("IDLE action in CONNECTED state");
        }
        else {
            invalidActionResponse(session);
        }
    }

    private void createdState(WebSocketSession session, WebSocketSession storedSession, TextMessage message) {
        if (isNotUserAdminInSession(storedSession)) {
            return;
        }

        BasicRequestBody requestBody = new Gson().fromJson(message.getPayload(),
                BasicRequestBody.class);

        if (SocketAction.START.equals(requestBody.getAction())) {
            //Change all states to waiting so that no modification are accepted
            try {
                Map<WebSocketSession, BasicResponseBody> waitingResponseMap = startGame.executeWaiting(storedSession);
                waitingResponseMap.entrySet().parallelStream()
                        .forEach(entry -> WebSocketOperations.sendObjectMessage(entry.getKey(), entry.getValue()));
            } catch (GameException e) {
                log.warn("Error starting game due to {}", e.getMessage());

                var responseBody = new ErrorResponseBody(1, "GAME NOT STARTED");
                WebSocketOperations.sendObjectMessage(session, responseBody);
                return;
            }

            //Change all status from waiting to playing
            Map<WebSocketSession, StartGameResponseBody> startingResponseMap = startGame.executeStarting(storedSession);
            startingResponseMap.entrySet().parallelStream()
                    .forEach(entry -> WebSocketOperations.sendObjectMessage(entry.getKey(), entry.getValue()));

            try {
                Map<WebSocketSession, BasicResponseBody> responseMap = victoryManager.executeTime(storedSession);
                responseMap.entrySet().parallelStream()
                        .forEach(entry -> WebSocketOperations.sendObjectMessage(entry.getKey(), entry.getValue()));
            } catch (GameException e) {
                log.error("Unable to finish game by time due to {}", e.getMessage());
            }
        }
        else if (SocketAction.DELETE.equals(requestBody.getAction())) {
            Map<WebSocketSession, BasicResponseBody> responseMap = deleteCreatedGame.execute(storedSession);
            responseMap.entrySet().parallelStream()
                    .forEach(entry -> WebSocketOperations.sendObjectMessage(entry.getKey(), entry.getValue()));
        }
        else {
            invalidActionResponse(session);
        }
    }

    private void joinedState(WebSocketSession session, WebSocketSession storedSession, TextMessage message) {
        BasicRequestBody requestBody = new Gson().fromJson(message.getPayload(),
                BasicRequestBody.class);

        if (SocketAction.EXIT.equals(requestBody.getAction())) {
            try {
                leaveGame.execute(storedSession);
            } catch (GameException e) {
                log.warn("Error exiting a joined game due to {}", e.getMessage());

                var responseBody = new ErrorResponseBody(1, "USER NOT EXITED FROM GAME");
                WebSocketOperations.sendObjectMessage(session, responseBody);
                return;
            }

            var responseBody = new BasicResponseBody(0, "USER EXITED FROM GAME",
                    WebSocketState.CONNECTED.toString(), WebSocketAction.EXIT.toString());
            WebSocketOperations.sendObjectMessage(session, responseBody);
        }
        else {
            invalidActionResponse(session);
        }
    }

    private void playingState(WebSocketSession session, WebSocketSession storedSession, TextMessage message) {
        BasicRequestBody requestBody = new Gson().fromJson(message.getPayload(),
                BasicRequestBody.class);

        if (SocketAction.DATA.equals(requestBody.getAction())) {
            var coordinatesRequestBody = new Gson().fromJson(message.getPayload(),
                    CoordinatesRequestBody.class);
            storeCoordinates.execute(storedSession, coordinatesRequestBody);
        }
        else if (SocketAction.REVIEW.equals(requestBody.getAction())) {
            boolean isCorrect = reviewCoordinates.execute(storedSession);

            ReviewGameResponseBody reviewGameResponseBody;

            if (isCorrect) {
                Score score;
                try {
                    score = updatePoints.execute(storedSession);
                } catch (GameException e) {
                    log.warn("Error updating points game due to {}", e.getMessage());

                    var responseBody = new ErrorResponseBody(1, "POINTS NOT UPDATED");
                    WebSocketOperations.sendObjectMessage(session, responseBody);
                    return;
                }
                Figures next = nextFigure.execute(storedSession);
                reviewGameResponseBody = new ReviewGameResponseBody(0, "FIGURE REVIEWED", WebSocketState.PLAYING.toString(),
                        WebSocketAction.REVIEW.toString(), true, next.toString(), score.getPoints());

                //Check points victory condition
                try {
                    Map<WebSocketSession, BasicResponseBody> responseMap = victoryManager.executePoints(storedSession, score);
                    responseMap.entrySet().parallelStream()
                            .forEach(entry -> WebSocketOperations.sendObjectMessage(entry.getKey(), entry.getValue()));
                } catch (GameException e) {
                    log.error("Unable to check victory condition by points due to {}", e.getMessage());
                }
            }
            else {
                reviewGameResponseBody = new ReviewGameResponseBody(0, "FIGURE REVIEWED", WebSocketState.PLAYING.toString(),
                        WebSocketAction.REVIEW.toString(), false, null, null);
            }
            WebSocketOperations.sendObjectMessage(session, reviewGameResponseBody);
        }
        else {
            invalidActionResponse(session);
        }
    }

    private boolean isNotUserAdminInSession(WebSocketSession session) {
        try {
            checkSocketUserAdmin.execute(session);
            return false;
        } catch (SessionException e) {
            log.warn("Invalid session received due to {}", e.getMessage());

            var responseBody = new ErrorResponseBody(1, "USER NOT ADMIN");
            WebSocketOperations.sendObjectMessage(session, responseBody);
            return true;
        }
    }

    private void invalidActionResponse(WebSocketSession session) {
        var responseBody = new ErrorResponseBody(1, "INVALID ACTION");
        WebSocketOperations.sendObjectMessage(session, responseBody);
    }
}
