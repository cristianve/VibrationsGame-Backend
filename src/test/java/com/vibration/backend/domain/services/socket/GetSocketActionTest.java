package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.SocketAction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetSocketActionTest {

    @Mock
    private SessionDataOperations sessionDataOperations;

    @Mock
    private WebSocketSession session;

    @Test
    void execute() {
        when(sessionDataOperations.getSocketAction(session)).thenReturn(SocketAction.IDLE);

        GetSocketAction getSocketAction = new GetSocketAction(sessionDataOperations);
        SocketAction socketAction = getSocketAction.execute(session);

        assertNotNull(socketAction);
        assertEquals(SocketAction.IDLE, socketAction);
        verify(sessionDataOperations, times(1)).getSocketAction(session);
    }
}