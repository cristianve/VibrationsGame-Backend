package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.SocketState;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class GetSocketState {

    private final SessionDataOperations sessionDataOperations;

    public GetSocketState(SessionDataOperations sessionDataOperations) {
        this.sessionDataOperations = sessionDataOperations;
    }

    public SocketState execute(WebSocketSession session) {
        return sessionDataOperations.getSocketState(session);
    }
}
