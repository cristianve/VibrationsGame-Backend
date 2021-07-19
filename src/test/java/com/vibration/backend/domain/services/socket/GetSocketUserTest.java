package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetSocketUserTest {

    @Mock
    private SessionDataOperations sessionDataOperations;

    @Mock
    private WebSocketSession session;

    @Mock
    private User user;

    @Test
    void execute() {
        when(sessionDataOperations.getUser(session)).thenReturn(user);

        GetSocketUser getSocketUser = new GetSocketUser(sessionDataOperations);
        User user = getSocketUser.execute(session);

        assertNotNull(user);
        assertEquals(this.user, user);
        verify(sessionDataOperations, times(1)).getUser(session);
    }
}