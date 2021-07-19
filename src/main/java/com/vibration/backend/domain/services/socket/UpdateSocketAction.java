package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.SocketAction;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class UpdateSocketAction {

    private final SessionDataOperations sessionDataOperations;

    public UpdateSocketAction(SessionDataOperations sessionDataOperations) {
        this.sessionDataOperations = sessionDataOperations;
    }

    public void execute(WebSocketSession session, SocketAction socketAction) {
        sessionDataOperations.updateSocketAction(session, socketAction);
    }
}
