package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.SocketState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetSocketStateTest {

    @Mock
    private SessionDataOperations sessionDataOperations;

    @Mock
    private WebSocketSession session;

    @Test
    void execute() {
        when(sessionDataOperations.getSocketState(session)).thenReturn(SocketState.CONNECTED);

        GetSocketState getSocketState = new GetSocketState(sessionDataOperations);
        SocketState socketState = getSocketState.execute(session);

        assertNotNull(socketState);
        assertEquals(SocketState.CONNECTED, socketState);
        verify(sessionDataOperations, times(1)).getSocketState(session);
    }
}