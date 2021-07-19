package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.UserAdmin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetSocketUserAdminTest {

    @Mock
    private SessionDataOperations sessionDataOperations;

    @Mock
    private WebSocketSession session;

    @Mock
    private UserAdmin userAdmin;

    @Test
    void execute() {
        when(sessionDataOperations.getUserAdmin(session)).thenReturn(userAdmin);

        GetSocketUserAdmin getSocketUserAdmin = new GetSocketUserAdmin(sessionDataOperations);
        UserAdmin userAdmin = getSocketUserAdmin.execute(session);

        assertNotNull(userAdmin);
        assertEquals(this.userAdmin, userAdmin);
        verify(sessionDataOperations, times(1)).getUserAdmin(session);
    }
}