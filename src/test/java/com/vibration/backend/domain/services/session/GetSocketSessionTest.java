package com.vibration.backend.domain.services.session;

import com.vibration.backend.domain.SessionRepository;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetSocketSessionTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private User user;

    @Mock
    private WebSocketSession session;

    @Test
    void execute() {
        when(sessionRepository.getSocketSession(user)).thenReturn(session);

        GetSocketSession getSocketSession = new GetSocketSession(sessionRepository);
        WebSocketSession session = getSocketSession.execute(user);

        assertNotNull(session);
        verify(sessionRepository, times(1)).getSocketSession(user);
    }
}