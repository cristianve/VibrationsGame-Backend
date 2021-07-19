package com.vibration.backend.application.services;

import com.vibration.backend.application.models.DeleteDataOperations;
import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.model.UserAdmin;
import com.vibration.backend.domain.services.coordinates.DeleteCoordinates;
import com.vibration.backend.domain.services.game.DeleteGame;
import com.vibration.backend.domain.services.playuserdata.DeletePlayUserData;
import com.vibration.backend.domain.services.ranking.DeleteRanking;
import com.vibration.backend.domain.services.session.DeleteSocketSession;
import com.vibration.backend.domain.services.session.GetSocketSession;
import com.vibration.backend.domain.services.socket.GetSocketGameId;
import com.vibration.backend.domain.services.socket.GetSocketUser;
import com.vibration.backend.domain.services.socket.GetSocketUserAdmin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DisconnectTest {

    @Mock
    private GetSocketSession getSocketSession;

    @Mock
    private DeleteDataOperations deleteDataOperations;

    @Mock
    private GetSocketAttributesOperations getSocketAttributesOperations;

    @Mock
    private GetSocketUserAdmin getSocketUserAdmin;

    @Mock
    private GetSocketGameId getSocketGameId;

    @Mock
    private GetSocketUser getSocketUser;

    @Mock
    private UserAdmin userAdmin;

    @Mock
    private GameId gameId;

    @Mock
    private User user;

    @Mock
    private DeleteRanking deleteRanking;

    @Mock
    private DeleteGame deleteGame;

    @Mock
    private DeletePlayUserData deletePlayUserData;

    @Mock
    private DeleteCoordinates deleteCoordinates;

    @Mock
    private DeleteSocketSession deleteSocketSession;

    @Mock
    private WebSocketSession session;

    @Mock
    private WebSocketSession storedSession;

    @Test
    void executeOKUserNotAdmin() {
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);
        when(getSocketSession.execute(user)).thenReturn(storedSession);
        when(getSocketAttributesOperations.getGetSocketUserAdmin()).thenReturn(getSocketUserAdmin);
        when(getSocketUserAdmin.execute(storedSession)).thenReturn(userAdmin);
        when(userAdmin.getAdmin()).thenReturn(Boolean.FALSE);
        when(deleteDataOperations.getDeletePlayUserData()).thenReturn(deletePlayUserData);
        doNothing().when(deletePlayUserData).execute(user);
        when(deleteDataOperations.getDeleteCoordinates()).thenReturn(deleteCoordinates);
        doNothing().when(deleteCoordinates).execute(user);
        when(deleteDataOperations.getDeleteSocketSession()).thenReturn(deleteSocketSession);
        doNothing().when(deleteSocketSession).execute(user);

        Disconnect disconnect = new Disconnect(getSocketSession, deleteDataOperations, getSocketAttributesOperations);
        disconnect.execute(session);

        verify(deletePlayUserData, times(1)).execute(user);
        verify(deleteCoordinates, times(1)).execute(user);
        verify(deleteSocketSession, times(1)).execute(user);
    }

    @Test
    void executeOKUserAdmin() {
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);
        when(getSocketSession.execute(user)).thenReturn(storedSession);
        when(getSocketAttributesOperations.getGetSocketUserAdmin()).thenReturn(getSocketUserAdmin);
        when(getSocketUserAdmin.execute(storedSession)).thenReturn(userAdmin);
        when(userAdmin.getAdmin()).thenReturn(Boolean.TRUE);
        when(getSocketAttributesOperations.getGetSocketGameId()).thenReturn(getSocketGameId);
        when(getSocketGameId.execute(storedSession)).thenReturn(gameId);
        when(deleteDataOperations.getDeleteRanking()).thenReturn(deleteRanking);
        doNothing().when(deleteRanking).execute();
        when(deleteDataOperations.getDeleteGame()).thenReturn(deleteGame);
        doNothing().when(deleteGame).execute(gameId);
        when(deleteDataOperations.getDeletePlayUserData()).thenReturn(deletePlayUserData);
        doNothing().when(deletePlayUserData).execute(user);
        when(deleteDataOperations.getDeleteCoordinates()).thenReturn(deleteCoordinates);
        doNothing().when(deleteCoordinates).execute(user);
        when(deleteDataOperations.getDeleteSocketSession()).thenReturn(deleteSocketSession);
        doNothing().when(deleteSocketSession).execute(user);

        Disconnect disconnect = new Disconnect(getSocketSession, deleteDataOperations, getSocketAttributesOperations);
        disconnect.execute(session);

        verify(deleteRanking, times(1)).execute();
        verify(deleteGame, times(1)).execute(gameId);
        verify(deletePlayUserData, times(1)).execute(user);
        verify(deleteCoordinates, times(1)).execute(user);
        verify(deleteSocketSession, times(1)).execute(user);
    }
}