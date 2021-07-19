package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.SocketAction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateSocketActionTest {

    @Mock
    private SessionDataOperations sessionDataOperations;

    @Mock
    private WebSocketSession session;

    @Test
    void execute() {
        doNothing().when(sessionDataOperations).updateSocketAction(session, SocketAction.IDLE);

        UpdateSocketAction updateSocketAction = new UpdateSocketAction(sessionDataOperations);
        updateSocketAction.execute(session, SocketAction.IDLE);

        verify(sessionDataOperations, times(1)).updateSocketAction(session, SocketAction.IDLE);
    }
}