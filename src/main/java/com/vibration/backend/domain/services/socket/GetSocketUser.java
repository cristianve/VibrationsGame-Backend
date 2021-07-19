package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class GetSocketUser {

    private final SessionDataOperations sessionDataOperations;

    public GetSocketUser(SessionDataOperations sessionDataOperations) {
        this.sessionDataOperations = sessionDataOperations;
    }

    public User execute(WebSocketSession session) {
        return sessionDataOperations.getUser(session);
    }
}
