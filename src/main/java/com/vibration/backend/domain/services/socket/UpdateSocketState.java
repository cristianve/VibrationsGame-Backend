package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.SocketState;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class UpdateSocketState {

    private final SessionDataOperations sessionDataOperations;

    public UpdateSocketState(SessionDataOperations sessionDataOperations) {
        this.sessionDataOperations = sessionDataOperations;
    }

    public void execute(WebSocketSession session, SocketState socketState) {
        sessionDataOperations.updateSocketState(session, socketState);
    }
}
