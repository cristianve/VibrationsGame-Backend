package com.vibration.backend.domain.services.session;

import com.vibration.backend.domain.SessionRepository;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class SaveSocketSession {

    private final SessionRepository sessionRepository;

    public SaveSocketSession(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void execute(User user, WebSocketSession session) {
        sessionRepository.saveSocketSession(user, session);
    }
}
