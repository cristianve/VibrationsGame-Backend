package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.application.models.UpdateSocketAttributesOperations;
import com.vibration.backend.domain.model.SocketAction;
import com.vibration.backend.domain.model.SocketState;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.services.session.SaveSocketSession;
import com.vibration.backend.domain.services.socket.GetSocketUser;
import com.vibration.backend.domain.services.socket.UpdateSocketAction;
import com.vibration.backend.domain.services.socket.UpdateSocketState;
import com.vibration.backend.domain.services.socket.UpdateSocketUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConnectTest {

    @Mock
    private SaveSocketSession saveSocketSession;

    @Mock
    private UpdateSocketAttributesOperations updateSocketAttributesOperations;

    @Mock
    private GetSocketAttributesOperations getSocketAttributesOperations;

    @Mock
    private GetSocketUser getSocketUser;

    @Mock
    private UpdateSocketUser updateSocketUser;

    @Mock
    private UpdateSocketState updateSocketState;

    @Mock
    private UpdateSocketAction updateSocketAction;

    @Mock
    private WebSocketSession session;

    @Mock
    private User user;

    @Test
    void execute() {
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);
        when(updateSocketAttributesOperations.getUpdateSocketUser()).thenReturn(updateSocketUser);
        doNothing().when(updateSocketUser).execute(session, user);
        when(updateSocketAttributesOperations.getUpdateSocketState()).thenReturn(updateSocketState);
        doNothing().when(updateSocketState).execute(session, SocketState.CONNECTED);
        when(updateSocketAttributesOperations.getUpdateSocketAction()).thenReturn(updateSocketAction);
        doNothing().when(updateSocketAction).execute(session, SocketAction.IDLE);
        doNothing().when(saveSocketSession).execute(user, session);

        Connect connect = new Connect(saveSocketSession, updateSocketAttributesOperations, getSocketAttributesOperations);
        connect.execute(session);

        verify(saveSocketSession, times(1)).execute(user, session);
    }
}