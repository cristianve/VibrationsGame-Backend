package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.UserAdmin;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class GetSocketUserAdmin {

    private final SessionDataOperations sessionDataOperations;

    public GetSocketUserAdmin(SessionDataOperations sessionDataOperations) {
        this.sessionDataOperations = sessionDataOperations;
    }

    public UserAdmin execute(WebSocketSession session) {
        return sessionDataOperations.getUserAdmin(session);
    }
}
