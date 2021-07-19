package com.vibration.backend.domain.services.session;

import com.vibration.backend.domain.SessionRepository;
import com.vibration.backend.domain.model.GameId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchSessionByGameIdTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private GameId gameId;

    @Mock
    private List<WebSocketSession> webSocketSessions;

    @Test
    void execute() {
        when(sessionRepository.searchSocketSessionByGameId(gameId)).thenReturn(webSocketSessions);

        SearchSessionByGameId searchSessionByGameId = new SearchSessionByGameId(sessionRepository);
        List<WebSocketSession> webSocketSessions = searchSessionByGameId.execute(gameId);

        assertNotNull(webSocketSessions);
        assertEquals(this.webSocketSessions, webSocketSessions);
        verify(sessionRepository, times(1)).searchSocketSessionByGameId(gameId);
    }
}