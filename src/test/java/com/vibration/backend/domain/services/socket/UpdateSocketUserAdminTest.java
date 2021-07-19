package com.vibration.backend.domain.services.socket;

import com.vibration.backend.domain.SessionDataOperations;
import com.vibration.backend.domain.model.UserAdmin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateSocketUserAdminTest {

    @Mock
    private SessionDataOperations sessionDataOperations;

    @Mock
    private WebSocketSession session;

    @Mock
    private UserAdmin userAdmin;

    @Test
    void execute() {
        doNothing().when(sessionDataOperations).updateUserAdmin(session, userAdmin);

        UpdateSocketUserAdmin updateSocketUserAdmin = new UpdateSocketUserAdmin(sessionDataOperations);
        updateSocketUserAdmin.execute(session, userAdmin);

        verify(sessionDataOperations, times(1)).updateUserAdmin(session, userAdmin);
    }
}