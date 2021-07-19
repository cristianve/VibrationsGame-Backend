package com.vibration.backend.domain.services.session;

import com.vibration.backend.domain.SessionRepository;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class GetSocketSession {

    private final SessionRepository sessionRepository;

    public GetSocketSession(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public WebSocketSession execute(User user) {
        return sessionRepository.getSocketSession(user);
    }
}
