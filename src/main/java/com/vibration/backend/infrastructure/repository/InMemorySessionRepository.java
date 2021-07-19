package com.vibration.backend.infrastructure.repository;

import com.vibration.backend.domain.SessionRepository;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.infrastructure.ws.WebSocketAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class InMemorySessionRepository implements SessionRepository {

    private static final Map<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();

    @Override
    public WebSocketSession getSocketSession(User user) {
        return webSocketSessionMap.get(user.getUserName());
    }

    @Override
    public List<WebSocketSession> searchSocketSessionByGameId(GameId gameId) {
        return webSocketSessionMap.values().stream()
                .filter(session -> gameId.getGameUuid().equals(WebSocketAttributes.getGameId(session)))
                .collect(Collectors.toList());
    }

    @Override
    public void saveSocketSession(User user, WebSocketSession session) {
        webSocketSessionMap.put(user.getUserName(), session);
    }

    @Override
    public void deleteSocketSession(User user) {
        webSocketSessionMap.remove(user.getUserName());
    }
}
