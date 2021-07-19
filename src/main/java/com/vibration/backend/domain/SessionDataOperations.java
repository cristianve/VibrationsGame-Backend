package com.vibration.backend.domain;

import com.vibration.backend.domain.model.*;
import org.springframework.web.socket.WebSocketSession;

public interface SessionDataOperations {

    void updateSocketState(WebSocketSession session, SocketState socketState);
    SocketState getSocketState(WebSocketSession session);
    void updateSocketAction(WebSocketSession session, SocketAction socketAction);
    SocketAction getSocketAction(WebSocketSession session);
    void updateGameId(WebSocketSession session, GameId gameId);
    GameId getGameId(WebSocketSession session);
    void updateUser(WebSocketSession session, User user);
    User getUser(WebSocketSession session);
    void updateUserAdmin(WebSocketSession session, UserAdmin userAdmin);
    UserAdmin getUserAdmin(WebSocketSession session);

}
