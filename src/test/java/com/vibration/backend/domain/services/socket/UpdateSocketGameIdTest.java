package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.GameId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateSocketGameIdTest {

    @Mock
    private SessionDataOperations sessionDataOperations;

    @Mock
    private WebSocketSession session;

    @Mock
    private GameId gameId;

    @Test
    void execute() {
        doNothing().when(sessionDataOperations).updateGameId(session, gameId);

        UpdateSocketGameId updateSocketGameId = new UpdateSocketGameId(sessionDataOperations);
        updateSocketGameId.execute(session, gameId);

        verify(sessionDataOperations, times(1)).updateGameId(session, gameId);
    }
}