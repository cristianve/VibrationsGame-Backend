package com.vibration.backend.infrastructure.ws;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.*;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;
import java.util.UUID;

@Component
public class WebSocketSessionData implements SessionDataOperations {

    private static final String ATTR_SOCKET_STATE = "SOCKET_STATE";
    private static final String ATTR_ACTION = "ACTION";
    private static final String ATTR_GAME_ID = "GAME_ID";
    private static final String ATTR_USERNAME = "USERNAME";
    private static final String ATTR_USER_ADMIN = "USER_ADMIN";

    @Override
    public void updateSocketState(WebSocketSession session, SocketState socketState) {
        session.getAttributes().put(ATTR_SOCKET_STATE, WebSocketState.valueOf(socketState.toString()));
    }

    @Override
    public SocketState getSocketState(WebSocketSession session) {
        var webSocketState = (WebSocketState) session.getAttributes().get(ATTR_SOCKET_STATE);
        return SocketState.valueOf(webSocketState.toString());
    }

    @Override
    public void updateSocketAction(WebSocketSession session, SocketAction socketAction) {
        session.getAttributes().put(ATTR_ACTION, WebSocketAction.valueOf(socketAction.toString()));
    }

    @Override
    public SocketAction getSocketAction(WebSocketSession session) {
        var webSocketAction = (WebSocketAction) session.getAttributes().get(ATTR_ACTION);
        return SocketAction.valueOf(webSocketAction.toString());
    }

    @Override
    public void updateGameId(WebSocketSession session, GameId gameId) {
        if (Objects.isNull(gameId)) {
            session.getAttributes().remove(ATTR_GAME_ID);
        }
        else {
            session.getAttributes().put(ATTR_GAME_ID, gameId.getGameUuid());
        }
    }

    @Override
    public GameId getGameId(WebSocketSession session) {
        var gameUuid = (UUID) session.getAttributes().get(ATTR_GAME_ID);
        return new GameId(gameUuid);
    }

    @Override
    public void updateUser(WebSocketSession session, User user) {
        session.getAttributes().put(ATTR_USERNAME, user.getUserName());
    }

    @Override
    public User getUser(WebSocketSession session) {
        var username = (String) session.getAttributes().get(ATTR_USERNAME);
        return new User(username);
    }

    @Override
    public void updateUserAdmin(WebSocketSession session, UserAdmin userAdmin) {
        session.getAttributes().put(ATTR_USER_ADMIN, userAdmin.getAdmin());
    }

    @Override
    public UserAdmin getUserAdmin(WebSocketSession session) {
        var userAdmin = (Boolean) session.getAttributes().get(ATTR_USER_ADMIN);
        return new UserAdmin(userAdmin);
    }
}
