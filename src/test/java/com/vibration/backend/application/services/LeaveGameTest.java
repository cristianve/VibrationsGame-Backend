package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.application.models.UpdateSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.*;
import com.vibration.backend.domain.services.game.GetGame;
import com.vibration.backend.domain.services.game.RemovePlayerFromGame;
import com.vibration.backend.domain.services.session.SaveSocketSession;
import com.vibration.backend.domain.services.socket.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveGameTest {

    @Mock
    private UpdateSocketAttributesOperations updateSocketAttributesOperations;

    @Mock
    private GetSocketAttributesOperations getSocketAttributesOperations;

    @Mock
    private GetGame getGame;

    @Mock
    private RemovePlayerFromGame removePlayerFromGame;

    @Mock
    private SaveSocketSession saveSocketSession;

    @Mock
    private GetSocketGameId getSocketGameId;

    @Mock
    private GetSocketUser getSocketUser;

    @Mock
    private UpdateSocketState updateSocketState;

    @Mock
    private UpdateSocketAction updateSocketAction;

    @Mock
    private UpdateSocketGameId updateSocketGameId;

    @Mock
    private WebSocketSession session;

    @Mock
    private User user;

    @Mock
    private GameId gameId;

    @Mock
    private Game game;

    @Test
    void executeThrowsGameExceptionWhenGameIsPlaying() throws GameException {
        when(getSocketAttributesOperations.getGetSocketGameId()).thenReturn(getSocketGameId);
        when(getSocketGameId.execute(session)).thenReturn(gameId);
        when(getGame.execute(gameId)).thenReturn(game);
        when(game.isPlaying()).thenReturn(Boolean.TRUE);

        LeaveGame leaveGame = new LeaveGame(updateSocketAttributesOperations,
                getSocketAttributesOperations, getGame, removePlayerFromGame, saveSocketSession);
        assertThrows(GameException.class, () -> leaveGame.execute(session));
    }

    @Test
    void executeThrowsGameExceptionWhenAssigningPlayerToGame() throws GameException {
        when(getSocketAttributesOperations.getGetSocketGameId()).thenReturn(getSocketGameId);
        when(getSocketGameId.execute(session)).thenReturn(gameId);
        when(getGame.execute(gameId)).thenReturn(game);
        when(game.isPlaying()).thenReturn(Boolean.FALSE);

        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);

        doThrow(GameException.class).when(removePlayerFromGame).execute(gameId, user);

        LeaveGame leaveGame = new LeaveGame(updateSocketAttributesOperations,
                getSocketAttributesOperations, getGame, removePlayerFromGame, saveSocketSession);
        assertThrows(GameException.class, () -> leaveGame.execute(session));
    }

    @Test
    void executeOK() throws GameException {
        when(getSocketAttributesOperations.getGetSocketGameId()).thenReturn(getSocketGameId);
        when(getSocketGameId.execute(session)).thenReturn(gameId);
        when(getGame.execute(gameId)).thenReturn(game);
        when(game.isPlaying()).thenReturn(Boolean.FALSE);

        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);

        doNothing().when(removePlayerFromGame).execute(gameId, user);

        when(updateSocketAttributesOperations.getUpdateSocketGameId()).thenReturn(updateSocketGameId);
        doNothing().when(updateSocketGameId).execute(session, null);
        when(updateSocketAttributesOperations.getUpdateSocketState()).thenReturn(updateSocketState);
        doNothing().when(updateSocketState).execute(session, SocketState.CONNECTED);
        when(updateSocketAttributesOperations.getUpdateSocketAction()).thenReturn(updateSocketAction);
        doNothing().when(updateSocketAction).execute(session, SocketAction.IDLE);
        doNothing().when(saveSocketSession).execute(user, session);

        LeaveGame leaveGame = new LeaveGame(updateSocketAttributesOperations,
                getSocketAttributesOperations, getGame, removePlayerFromGame, saveSocketSession);
        leaveGame.execute(session);

        verify(saveSocketSession, times(1)).execute(user, session);
    }
}