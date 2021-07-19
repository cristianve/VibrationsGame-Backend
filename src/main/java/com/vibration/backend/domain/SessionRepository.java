package com.vibration.backend.domain;

import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.User;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

public interface SessionRepository {

    WebSocketSession getSocketSession(User user);
    List<WebSocketSession> searchSocketSessionByGameId(GameId gameId);
    void saveSocketSession(User user, WebSocketSession session);
    void deleteSocketSession(User user);

}
