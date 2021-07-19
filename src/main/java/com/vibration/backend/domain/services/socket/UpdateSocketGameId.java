package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.GameId;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class UpdateSocketGameId {

    private final SessionDataOperations sessionDataOperations;

    public UpdateSocketGameId(SessionDataOperations sessionDataOperations) {
        this.sessionDataOperations = sessionDataOperations;
    }

    public void execute(WebSocketSession session, GameId gameId) {
        sessionDataOperations.updateGameId(session, gameId);
    }
}
