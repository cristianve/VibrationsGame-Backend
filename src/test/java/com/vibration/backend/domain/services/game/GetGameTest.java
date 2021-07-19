package com.vibration.backend.domain.services.game;

import com.vibration.backend.domain.GameRepository;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.model.GameId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGameTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameId gameId;

    @Mock
    private Game game;

    @Test
    void executeThrowsGameException() throws GameException {
        when(gameRepository.getGame(gameId)).thenThrow(GameException.class);

        GetGame getGame = new GetGame(gameRepository);
        assertThrows(GameException.class, () -> getGame.execute(gameId));
    }

    @Test
    void execute() throws GameException {
        when(gameRepository.getGame(gameId)).thenReturn(game);

        GetGame getGame = new GetGame(gameRepository);
        Game game = getGame.execute(gameId);

        assertNotNull(game);
        assertEquals(this.game, game);
    }
}