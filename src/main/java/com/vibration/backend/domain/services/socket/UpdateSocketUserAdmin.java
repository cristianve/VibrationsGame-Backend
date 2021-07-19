package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.UserAdmin;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class UpdateSocketUserAdmin {

    private final SessionDataOperations sessionDataOperations;

    public UpdateSocketUserAdmin(SessionDataOperations sessionDataOperations) {
        this.sessionDataOperations = sessionDataOperations;
    }

    public void execute(WebSocketSession session, UserAdmin userAdmin) {
        sessionDataOperations.updateUserAdmin(session, userAdmin);
    }
}
