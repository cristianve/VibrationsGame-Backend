package com.vibration.backend.domain.services.session;

import com.vibration.backend.domain.SessionRepository;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveSocketSessionTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private WebSocketSession session;

    @Mock
    private User user;

    @Test
    void execute() {
        doNothing().when(sessionRepository).saveSocketSession(user, session);

        SaveSocketSession saveSocketSession = new SaveSocketSession(sessionRepository);
        saveSocketSession.execute(user, session);

        verify(sessionRepository, times(1)).saveSocketSession(user, session);
    }
}