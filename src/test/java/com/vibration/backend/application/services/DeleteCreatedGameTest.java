package com.vibration.backend.application.services;

import com.vibration.backend.application.models.DeleteDataOperations;
import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.application.models.UpdateSocketAttributesOperations;
import com.vibration.backend.domain.model.*;
import com.vibration.backend.domain.services.game.DeleteGame;
import com.vibration.backend.domain.services.session.SaveSocketSession;
import com.vibration.backend.domain.services.session.SearchSessionByGameId;
import com.vibration.backend.domain.services.socket.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCreatedGameTest {

    @Mock
    private UpdateSocketAttributesOperations updateSocketAttributesOperations;

    @Mock
    private GetSocketAttributesOperations getSocketAttributesOperations;

    @Mock
    private SearchSessionByGameId searchSessionByGameId;

    @Mock
    private DeleteDataOperations deleteDataOperations;

    @Mock
    private SaveSocketSession saveSocketSession;

    @Mock
    private UpdateSocketState updateSocketState;

    @Mock
    private UpdateSocketAction updateSocketAction;

    @Mock
    private UpdateSocketGameId updateSocketGameId;

    @Mock
    private UpdateSocketUserAdmin updateSocketUserAdmin;

    @Mock
    private GetSocketUser getSocketUser;

    @Mock
    private GetSocketGameId getSocketGameId;

    @Mock
    private WebSocketSession session;

    @Mock
    private GameId gameId;

    @Mock
    private User user;

    @Mock
    private DeleteGame deleteGame;

    @Mock
    private WebSocketSession socketSessionInList;

    @Test
    void executeOK() {
        List<WebSocketSession> webSocketSessionList = new ArrayList<>();
        webSocketSessionList.add(socketSessionInList);

        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(any(WebSocketSession.class))).thenReturn(user);
        when(user.getUserName()).thenReturn("UserName");
        when(getSocketAttributesOperations.getGetSocketGameId()).thenReturn(getSocketGameId);
        when(getSocketGameId.execute(session)).thenReturn(gameId);
        when(deleteDataOperations.getDeleteGame()).thenReturn(deleteGame);
        doNothing().when(deleteGame).execute(gameId);
        when(searchSessionByGameId.execute(gameId)).thenReturn(webSocketSessionList);
        when(updateSocketAttributesOperations.getUpdateSocketGameId()).thenReturn(updateSocketGameId);
        doNothing().when(updateSocketGameId).execute(socketSessionInList, null);
        when(updateSocketAttributesOperations.getUpdateSocketUserAdmin()).thenReturn(updateSocketUserAdmin);
        doNothing().when(updateSocketUserAdmin).execute(eq(socketSessionInList), any(UserAdmin.class));
        when(updateSocketAttributesOperations.getUpdateSocketState()).thenReturn(updateSocketState);
        doNothing().when(updateSocketState).execute(socketSessionInList, SocketState.CONNECTED);
        when(updateSocketAttributesOperations.getUpdateSocketAction()).thenReturn(updateSocketAction);
        doNothing().when(updateSocketAction).execute(socketSessionInList, SocketAction.IDLE);
        doNothing().when(saveSocketSession).execute(user, socketSessionInList);

        DeleteCreatedGame deleteCreatedGame = new DeleteCreatedGame(updateSocketAttributesOperations,
                getSocketAttributesOperations, searchSessionByGameId, deleteDataOperations, saveSocketSession);
        assertDoesNotThrow(() -> deleteCreatedGame.execute(session));

        verify(saveSocketSession, times(1)).execute(user, socketSessionInList);
    }
}