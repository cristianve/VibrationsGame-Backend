package com.vibration.backend.domain.services.session;

import com.vibration.backend.domain.SessionRepository;
import com.vibration.backend.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class DeleteSocketSession {

    private final SessionRepository sessionRepository;

    public DeleteSocketSession(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void execute(User user) {
        sessionRepository.deleteSocketSession(user);
    }
}
