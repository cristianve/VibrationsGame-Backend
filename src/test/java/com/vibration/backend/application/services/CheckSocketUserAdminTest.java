package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.SessionException;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.model.UserAdmin;
import com.vibration.backend.domain.services.session.GetSocketSession;
import com.vibration.backend.domain.services.socket.GetSocketUser;
import com.vibration.backend.domain.services.socket.GetSocketUserAdmin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckSocketUserAdminTest {

    @Mock
    private GetSocketAttributesOperations getSocketAttributesOperations;

    @Mock
    private GetSocketSession getSocketSession;

    @Mock
    private GetSocketUserAdmin getSocketUserAdmin;

    @Mock
    private GetSocketUser getSocketUser;

    @Mock
    private WebSocketSession session;

    @Mock
    private WebSocketSession sessionStored;

    @Mock
    private User user;

    @Mock
    private UserAdmin userAdmin;

    @Mock
    private UserAdmin userAdminStored;

    @ParameterizedTest
    @NullSource
    @ValueSource(booleans = {false})
    void executeThrowsSocketSessionExceptionWhenSessionUserAminIsNotTrue(Boolean userAdminInSession) {
        when(getSocketAttributesOperations.getGetSocketUserAdmin()).thenReturn(getSocketUserAdmin);
        when(getSocketUserAdmin.execute(session)).thenReturn(userAdmin);
        when(userAdmin.getAdmin()).thenReturn(userAdminInSession);

        CheckSocketUserAdmin checkSocketUserAdmin = new CheckSocketUserAdmin(getSocketAttributesOperations,
                getSocketSession);
        assertThrows(SessionException.class, () -> checkSocketUserAdmin.execute(session));
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(booleans = {false})
    void executeThrowsSocketSessionExceptionWhenStoredSessionUserAminIsNotTrue(Boolean userAdminInSession) {
        when(getSocketAttributesOperations.getGetSocketUserAdmin()).thenReturn(getSocketUserAdmin);
        when(getSocketUserAdmin.execute(session)).thenReturn(userAdmin);
        when(userAdmin.getAdmin()).thenReturn(Boolean.TRUE);

        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);
        when(getSocketSession.execute(user)).thenReturn(sessionStored);
        when(getSocketUserAdmin.execute(sessionStored)).thenReturn(userAdminStored);
        when(userAdminStored.getAdmin()).thenReturn(userAdminInSession);

        CheckSocketUserAdmin checkSocketUserAdmin = new CheckSocketUserAdmin(getSocketAttributesOperations,
                getSocketSession);
        assertThrows(SessionException.class, () -> checkSocketUserAdmin.execute(session));
    }

    @Test
    void executeOK() {
        when(getSocketAttributesOperations.getGetSocketUserAdmin()).thenReturn(getSocketUserAdmin);
        when(getSocketUserAdmin.execute(session)).thenReturn(userAdmin);
        when(userAdmin.getAdmin()).thenReturn(Boolean.TRUE);

        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);
        when(getSocketSession.execute(user)).thenReturn(sessionStored);
        when(getSocketUserAdmin.execute(sessionStored)).thenReturn(userAdminStored);
        when(userAdminStored.getAdmin()).thenReturn(Boolean.TRUE);

        CheckSocketUserAdmin checkSocketUserAdmin = new CheckSocketUserAdmin(getSocketAttributesOperations,
                getSocketSession);
        assertDoesNotThrow(() -> checkSocketUserAdmin.execute(session));
    }
}