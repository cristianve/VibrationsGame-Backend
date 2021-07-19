package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.SessionException;
import com.vibration.backend.domain.model.SocketState;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.services.session.GetSocketSession;
import com.vibration.backend.domain.services.socket.GetSocketState;
import com.vibration.backend.domain.services.socket.GetSocketUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckSocketSessionTest {

    @Mock
    private GetSocketAttributesOperations getSocketAttributesOperations;

    @Mock
    private GetSocketSession getSocketSession;

    @Mock
    private GetSocketState getSocketState;

    @Mock
    private GetSocketUser getSocketUser;

    @Mock
    private WebSocketSession session;

    @Mock
    private WebSocketSession sessionStored;

    @Mock
    private User user;

    @ParameterizedTest
    @NullSource
    void executeThrowsSocketSessionExceptionWhenReceivedSocketSessionIsNull(WebSocketSession session) {
        CheckSocketSession checkSocketSession = new CheckSocketSession(getSocketAttributesOperations, getSocketSession);
        assertThrows(SessionException.class, () -> checkSocketSession.execute(session));
    }

    @Test
    void executeThrowsSocketSessionExceptionWhenStoredSocketSessionNotFound() {
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);
        when(getSocketSession.execute(user)).thenReturn(null);

        CheckSocketSession checkSocketSession = new CheckSocketSession(getSocketAttributesOperations, getSocketSession);
        assertThrows(SessionException.class, () -> checkSocketSession.execute(session));
    }

    @Test
    void executeThrowsSocketSessionExceptionWhenSocketStateIsDifferent() {
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);
        when(getSocketSession.execute(user)).thenReturn(sessionStored);
        when(getSocketAttributesOperations.getGetSocketState()).thenReturn(getSocketState);
        when(getSocketState.execute(sessionStored)).thenReturn(null);

        CheckSocketSession checkSocketSession = new CheckSocketSession(getSocketAttributesOperations, getSocketSession);
        assertThrows(SessionException.class, () -> checkSocketSession.execute(session));
    }

    @Test
    void executeOK() {
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);
        when(getSocketSession.execute(user)).thenReturn(sessionStored);
        when(getSocketAttributesOperations.getGetSocketState()).thenReturn(getSocketState);
        when(getSocketState.execute(sessionStored)).thenReturn(SocketState.CONNECTED);

        CheckSocketSession checkSocketSession = new CheckSocketSession(getSocketAttributesOperations, getSocketSession);
        assertDoesNotThrow(() -> checkSocketSession.execute(session));
    }
}