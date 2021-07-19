package com.vibration.backend.domain.services.session;

import com.vibration.backend.domain.SessionRepository;
import com.vibration.backend.domain.model.GameId;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Component
public class SearchSessionByGameId {

    private final SessionRepository sessionRepository;

    public SearchSessionByGameId(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<WebSocketSession> execute(GameId gameId) {
        return sessionRepository.searchSocketSessionByGameId(gameId);
    }
}
