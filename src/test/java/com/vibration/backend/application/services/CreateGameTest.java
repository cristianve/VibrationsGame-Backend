package com.vibration.backend.application.services;

import com.vibration.backend.application.dto.CreateGameRequestBody;
import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.application.models.UpdateSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.*;
import com.vibration.backend.domain.services.game.CheckGameNameExists;
import com.vibration.backend.domain.services.game.SaveGame;
import com.vibration.backend.domain.services.session.*;
import com.vibration.backend.domain.services.socket.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.socket.WebSocketSession;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CreateGameTest {

    @Mock
    private UpdateSocketAttributesOperations updateSocketAttributesOperations;

    @Mock
    private GetSocketAttributesOperations getSocketAttributesOperations;

    @Mock
    private SaveGame saveGame;

    @Mock
    private SaveSocketSession saveSocketSession;

    @Mock
    private CheckGameNameExists checkGameNameExists;

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
    private WebSocketSession session;

    @Mock
    private CreateGameRequestBody createGameRequestBody;

    @Mock
    private GameId gameId;

    @Mock
    private User user;

    private static Stream<Arguments> throwsGameExceptionInvalidRequestData() {
        return Stream.of(
                Arguments.of(null, 10, VictoryConditions.POINTS, 1000),
                Arguments.of("", 10, VictoryConditions.POINTS, 1000),
                Arguments.of("  ", 10, VictoryConditions.POINTS, 1000)
        );
    }

    private static Stream<Arguments> okRequestData() {
        return Stream.of(
                Arguments.of("Name", null, VictoryConditions.POINTS, 1000),
                Arguments.of("Name", 10, null, 1000),
                Arguments.of("Name", 10, VictoryConditions.POINTS, null)
        );
    }

    @ParameterizedTest
    @MethodSource("throwsGameExceptionInvalidRequestData")
    void executeThrowsGameExceptionWhenInvalidGameData(String gameName,
                                                       Integer maxPlayers,
                                                       VictoryConditions victoryConditions,
                                                       Integer condition) {
        when(createGameRequestBody.getName()).thenReturn(gameName);
        when(createGameRequestBody.getMaxPlayers()).thenReturn(maxPlayers);
        when(createGameRequestBody.getVictoryCondition()).thenReturn(victoryConditions);
        when(createGameRequestBody.getCondition()).thenReturn(condition);

        CreateGame createGame = new CreateGame(updateSocketAttributesOperations, getSocketAttributesOperations,
                saveGame, checkGameNameExists, saveSocketSession);
        assertThrows(GameException.class, () -> createGame.execute(session, createGameRequestBody));
    }

    @Test
    void executeThrowsGameExceptionWhenGameNameExists() {
        when(createGameRequestBody.getName()).thenReturn("NameExists");
        when(createGameRequestBody.getMaxPlayers()).thenReturn(10);
        when(createGameRequestBody.getVictoryCondition()).thenReturn(VictoryConditions.TIME);
        when(createGameRequestBody.getCondition()).thenReturn(60000);
        when(checkGameNameExists.execute(any(Game.class))).thenReturn(Boolean.TRUE);

        CreateGame createGame = new CreateGame(updateSocketAttributesOperations, getSocketAttributesOperations,
                saveGame, checkGameNameExists, saveSocketSession);
        assertThrows(GameException.class, () -> createGame.execute(session, createGameRequestBody));
    }

    @ParameterizedTest
    @MethodSource("okRequestData")
    void executeOK(String gameName,
                   Integer maxPlayers,
                   VictoryConditions victoryConditions,
                   Integer condition) {
        when(createGameRequestBody.getName()).thenReturn(gameName);
        when(createGameRequestBody.getMaxPlayers()).thenReturn(maxPlayers);
        when(createGameRequestBody.getVictoryCondition()).thenReturn(victoryConditions);
        when(createGameRequestBody.getCondition()).thenReturn(condition);
        when(checkGameNameExists.execute(any(Game.class))).thenReturn(Boolean.FALSE);
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);
        when(saveGame.execute(any(Game.class))).thenReturn(gameId);
        when(updateSocketAttributesOperations.getUpdateSocketGameId()).thenReturn(updateSocketGameId);
        doNothing().when(updateSocketGameId).execute(session, gameId);
        when(updateSocketAttributesOperations.getUpdateSocketUserAdmin()).thenReturn(updateSocketUserAdmin);
        doNothing().when(updateSocketUserAdmin).execute(session, new UserAdmin(Boolean.TRUE));
        when(updateSocketAttributesOperations.getUpdateSocketState()).thenReturn(updateSocketState);
        doNothing().when(updateSocketState).execute(session, SocketState.CREATED);
        when(updateSocketAttributesOperations.getUpdateSocketAction()).thenReturn(updateSocketAction);
        doNothing().when(updateSocketAction).execute(session, SocketAction.IDLE);
        doNothing().when(saveSocketSession).execute(user, session);

        CreateGame createGame = new CreateGame(updateSocketAttributesOperations, getSocketAttributesOperations,
                saveGame, checkGameNameExists, saveSocketSession);
        assertDoesNotThrow(() -> createGame.execute(session, createGameRequestBody));

        verify(saveSocketSession, times(1)).execute(user, session);
    }
}