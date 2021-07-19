package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.services.game.GetGame;
import com.vibration.backend.domain.services.socket.GetSocketGameId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGameInformationTest {

    @Mock
    private GetSocketAttributesOperations getSocketAttributesOperations;

    @Mock
    private GetGame getGame;

    @Mock
    private WebSocketSession session;

    @Mock
    private GetSocketGameId getSocketGameId;

    @Mock
    private GameId gameId;

    @Mock
    private Game game;

    @Test
    void executeThrowsGameException() throws GameException {
        when(getSocketAttributesOperations.getGetSocketGameId()).thenReturn(getSocketGameId);
        when(getSocketGameId.execute(session)).thenReturn(gameId);
        when(getGame.execute(gameId)).thenThrow(GameException.class);

        GetGameInformation getGameInformation = new GetGameInformation(getSocketAttributesOperations, getGame);
        assertThrows(GameException.class, () -> getGameInformation.execute(session));
    }

    @Test
    void execute() throws GameException {
        when(getSocketAttributesOperations.getGetSocketGameId()).thenReturn(getSocketGameId);
        when(getSocketGameId.execute(session)).thenReturn(gameId);
        when(getGame.execute(gameId)).thenReturn(game);

        GetGameInformation getGameInformation = new GetGameInformation(getSocketAttributesOperations, getGame);
        Game game = getGameInformation.execute(session);

        assertNotNull(game);
        assertEquals(this.game, game);
    }
}