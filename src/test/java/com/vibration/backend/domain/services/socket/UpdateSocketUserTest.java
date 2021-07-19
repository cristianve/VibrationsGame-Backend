package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateSocketUserTest {

    @Mock
    private SessionDataOperations sessionDataOperations;

    @Mock
    private WebSocketSession session;

    @Mock
    private User user;

    @Test
    void execute() {
        doNothing().when(sessionDataOperations).updateUser(session, user);

        UpdateSocketUser updateSocketUser = new UpdateSocketUser(sessionDataOperations);
        updateSocketUser.execute(session, user);

        verify(sessionDataOperations, times(1)).updateUser(session, user);
    }
}