package com.vibration.backend.application.services;

import com.vibration.backend.application.dto.BasicResponseBody;
import com.vibration.backend.application.dto.StartGameResponseBody;
import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.application.models.UpdateSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.*;
import com.vibration.backend.domain.services.figures.SelectFigure;
import com.vibration.backend.domain.services.game.GetGame;
import com.vibration.backend.domain.services.game.SaveGame;
import com.vibration.backend.domain.services.playuserdata.SavePlayUserData;
import com.vibration.backend.domain.services.score.SaveScore;
import com.vibration.backend.domain.services.session.SaveSocketSession;
import com.vibration.backend.domain.services.session.SearchSessionByGameId;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StartGame {

    private final UpdateSocketAttributesOperations updateSocketAttributesOperations;
    private final GetSocketAttributesOperations getSocketAttributesOperations;
    private final SaveSocketSession saveSocketSession;
    private final SearchSessionByGameId searchSessionByGameId;
    private final SaveScore saveScore;
    private final SelectFigure selectFigure;
    private final SavePlayUserData savePlayUserData;
    private final GetGame getGame;
    private final SaveGame saveGame;

    public StartGame(UpdateSocketAttributesOperations updateSocketAttributesOperations,
                     GetSocketAttributesOperations getSocketAttributesOperations,
                     SaveSocketSession saveSocketSession,
                     SearchSessionByGameId searchSessionByGameId,
                     SaveScore saveScore,
                     SelectFigure selectFigure,
                     SavePlayUserData savePlayUserData, GetGame getGame, SaveGame saveGame) {
        this.updateSocketAttributesOperations = updateSocketAttributesOperations;
        this.getSocketAttributesOperations = getSocketAttributesOperations;
        this.saveSocketSession = saveSocketSession;
        this.searchSessionByGameId = searchSessionByGameId;
        this.saveScore = saveScore;
        this.selectFigure = selectFigure;
        this.savePlayUserData = savePlayUserData;
        this.getGame = getGame;
        this.saveGame = saveGame;
    }

    public Map<WebSocketSession, BasicResponseBody> executeWaiting(WebSocketSession session) throws GameException {
        Map<WebSocketSession, BasicResponseBody> responseBodyMap = new ConcurrentHashMap<>();

        var gameId = getSocketAttributesOperations.getGetSocketGameId().execute(session);
        var game = getGame.execute(gameId);
        game.setPlaying(true);
        saveGame.execute(game);

        List<WebSocketSession> webSocketSessionList = searchSessionByGameId.execute(gameId);

        webSocketSessionList.parallelStream().forEach(userSession -> {
            updateSocketAttributesOperations.getUpdateSocketState().execute(userSession, SocketState.WAITING);
            updateSocketAttributesOperations.getUpdateSocketAction().execute(userSession, SocketAction.IDLE);

            var user = getSocketAttributesOperations.getGetSocketUser().execute(userSession);
            saveSocketSession.execute(user, userSession);

            var responseBody = new BasicResponseBody(0, "GAME STARTING",
                    SocketState.WAITING.toString(), SocketAction.IDLE.toString());

            responseBodyMap.put(userSession, responseBody);
        });

        return responseBodyMap;
    }

    public Map<WebSocketSession, StartGameResponseBody> executeStarting(WebSocketSession session) {
        Map<WebSocketSession, StartGameResponseBody> responseBodyMap = new ConcurrentHashMap<>();

        var gameId = getSocketAttributesOperations.getGetSocketGameId().execute(session);
        List<WebSocketSession> webSocketSessionList = searchSessionByGameId.execute(gameId);

        var userRequester = getSocketAttributesOperations.getGetSocketUser().execute(session);

        webSocketSessionList.parallelStream().forEach(userSession -> {
            var user = getSocketAttributesOperations.getGetSocketUser().execute(userSession);

            StartGameResponseBody responseBody;

            if (userRequester.getUserName().equals(user.getUserName())) {
                //User admin
                updateSocketAttributesOperations.getUpdateSocketState().execute(userSession, SocketState.RANKING);
                responseBody = new StartGameResponseBody(0, "GAME START", SocketState.RANKING.toString(),
                        SocketAction.IDLE.toString(), null);
            }
            else {
                updateSocketAttributesOperations.getUpdateSocketState().execute(userSession, SocketState.PLAYING);
                saveScore.execute(user, new Score(0));

                Figures figure = selectFigure.execute();

                var playUserData = new PlayUserData(user, figure, Instant.now());
                savePlayUserData.execute(playUserData);

                responseBody = new StartGameResponseBody(0, "GAME START", SocketState.PLAYING.toString(),
                        SocketAction.IDLE.toString(), figure.toString());
            }
            updateSocketAttributesOperations.getUpdateSocketAction().execute(userSession, SocketAction.IDLE);

            saveSocketSession.execute(user, userSession);

            responseBodyMap.put(userSession, responseBody);
        });

        return responseBodyMap;
    }
}
