package com.vibration.backend.domain.services.session;

import com.vibration.backend.domain.SessionRepository;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteSocketSessionTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private User user;

    @Test
    void execute() {
        doNothing().when(sessionRepository).deleteSocketSession(any());

        DeleteSocketSession deleteSocketSession = new DeleteSocketSession(sessionRepository);
        deleteSocketSession.execute(user);

        verify(sessionRepository, times(1)).deleteSocketSession(any());
    }
}