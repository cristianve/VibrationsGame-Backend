package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.SocketState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateSocketStateTest {

    @Mock
    private SessionDataOperations sessionDataOperations;

    @Mock
    private WebSocketSession session;

    @Test
    void execute() {
        doNothing().when(sessionDataOperations).updateSocketState(session, SocketState.CONNECTED);

        UpdateSocketState updateSocketState = new UpdateSocketState(sessionDataOperations);
        updateSocketState.execute(session, SocketState.CONNECTED);

        verify(sessionDataOperations, times(1)).updateSocketState(session, SocketState.CONNECTED);
    }
}