package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.GameId;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class GetSocketGameId {

    private final SessionDataOperations sessionDataOperations;

    public GetSocketGameId(SessionDataOperations sessionDataOperations) {
        this.sessionDataOperations = sessionDataOperations;
    }

    public GameId execute(WebSocketSession session) {
        return sessionDataOperations.getGameId(session);
    }
}
