package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.GameId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetSocketGameIdTest {

    @Mock
    private SessionDataOperations sessionDataOperations;

    @Mock
    private WebSocketSession session;

    @Mock
    private GameId gameId;

    @Test
    void execute() {
        when(sessionDataOperations.getGameId(session)).thenReturn(gameId);

        GetSocketGameId getSocketGameId = new GetSocketGameId(sessionDataOperations);
        GameId gameId = getSocketGameId.execute(session);

        assertNotNull(gameId);
        assertEquals(this.gameId, gameId);
        verify(sessionDataOperations, times(1)).getGameId(session);
    }
}