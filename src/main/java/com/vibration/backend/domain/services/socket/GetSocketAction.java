package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.SocketAction;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class GetSocketAction {

    private final SessionDataOperations sessionDataOperations;

    public GetSocketAction(SessionDataOperations sessionDataOperations) {
        this.sessionDataOperations = sessionDataOperations;
    }

    public SocketAction execute(WebSocketSession session) {
        return sessionDataOperations.getSocketAction(session);
    }
}
