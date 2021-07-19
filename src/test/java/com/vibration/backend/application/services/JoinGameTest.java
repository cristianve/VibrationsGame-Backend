package com.vibration.backend.application.services;

import com.vibration.backend.application.dto.AssignUserToGameRequestBody;
import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.application.models.UpdateSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.SocketState;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.services.game.AssignPlayerToGame;
import com.vibration.backend.domain.services.game.GetGame;
import com.vibration.backend.domain.services.session.SaveSocketSession;
import com.vibration.backend.domain.services.socket.GetSocketUser;
import com.vibration.backend.domain.services.socket.UpdateSocketGameId;
import com.vibration.backend.domain.services.socket.UpdateSocketState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JoinGameTest {

    @Mock
    private UpdateSocketAttributesOperations updateSocketAttributesOperations;

    @Mock
    private GetSocketAttributesOperations getSocketAttributesOperations;

    @Mock
    private AssignPlayerToGame assignPlayerToGame;

    @Mock
    private SaveSocketSession saveSocketSession;

    @Mock
    private GetGame getGame;

    @Mock
    private GetSocketUser getSocketUser;

    @Mock
    private UpdateSocketGameId updateSocketGameId;

    @Mock
    private UpdateSocketState updateSocketState;

    @Mock
    private WebSocketSession session;

    @Mock
    private User user;

    @Mock
    private Game game;

    @Mock
    private AssignUserToGameRequestBody requestBody;

    private final UUID uuid = UUID.randomUUID();

    @Test
    void executeThrowsGameExceptionWhenGameIsPlaying() throws GameException {
        when(requestBody.getGameId()).thenReturn(uuid);
        when(getGame.execute(any(GameId.class))).thenReturn(game);
        when(game.isPlaying()).thenReturn(Boolean.TRUE);

        JoinGame joinGame = new JoinGame(updateSocketAttributesOperations, getSocketAttributesOperations,
                assignPlayerToGame, saveSocketSession, getGame);
        assertThrows(GameException.class, () -> joinGame.execute(session, requestBody));
    }

    @Test
    void executeThrowsGameExceptionWhenAssigningPlayerToGame() throws GameException {
        when(requestBody.getGameId()).thenReturn(uuid);
        when(getGame.execute(any(GameId.class))).thenReturn(game);
        when(game.isPlaying()).thenReturn(Boolean.FALSE);
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);
        doThrow(GameException.class).when(assignPlayerToGame).execute(any(GameId.class), any(User.class));

        JoinGame joinGame = new JoinGame(updateSocketAttributesOperations, getSocketAttributesOperations,
                assignPlayerToGame, saveSocketSession, getGame);
        assertThrows(GameException.class, () -> joinGame.execute(session, requestBody));
    }

    @Test
    void executeOk() throws GameException {
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);

        when(requestBody.getGameId()).thenReturn(uuid);
        when(getGame.execute(any(GameId.class))).thenReturn(game);
        when(game.isPlaying()).thenReturn(Boolean.FALSE);
        doNothing().when(assignPlayerToGame).execute(any(GameId.class), any(User.class));
        when(updateSocketAttributesOperations.getUpdateSocketGameId()).thenReturn(updateSocketGameId);
        doNothing().when(updateSocketGameId).execute(eq(session), any(GameId.class));
        when(updateSocketAttributesOperations.getUpdateSocketState()).thenReturn(updateSocketState);
        doNothing().when(updateSocketState).execute(session, SocketState.JOINED);
        doNothing().when(saveSocketSession).execute(user, session);

        JoinGame joinGame = new JoinGame(updateSocketAttributesOperations, getSocketAttributesOperations,
                assignPlayerToGame, saveSocketSession, getGame);
        assertDoesNotThrow(() -> joinGame.execute(session, requestBody));

        verify(saveSocketSession, times(1)).execute(user, session);
    }
}