package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class UpdateSocketUser {

    private final SessionDataOperations sessionDataOperations;

    public UpdateSocketUser(SessionDataOperations sessionDataOperations) {
        this.sessionDataOperations = sessionDataOperations;
    }

    public void execute(WebSocketSession session, User user) {
        sessionDataOperations.updateUser(session, user);
    }
}
