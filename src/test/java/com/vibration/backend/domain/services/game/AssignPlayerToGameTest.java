package com.vibration.backend.domain.services.game;

import com.vibration.backend.domain.GameRepository;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignPlayerToGameTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameId gameId;

    @Mock
    private User user;

    @Test
    void executeShouldAddPlayerToGame() throws GameException {
        doNothing().when(gameRepository).addPlayer(gameId, user);

        AssignPlayerToGame assignPlayerToGame = new AssignPlayerToGame(gameRepository);
        assertDoesNotThrow(() -> assignPlayerToGame.execute(gameId, user));

        verify(gameRepository, times(1)).addPlayer(gameId, user);
    }

    @Test
    void executeShouldThrowGameException() throws GameException {
        doThrow(GameException.class).when(gameRepository).addPlayer(gameId, user);

        AssignPlayerToGame assignPlayerToGame = new AssignPlayerToGame(gameRepository);
        assertThrows(GameException.class, () -> assignPlayerToGame.execute(gameId, user));

        verify(gameRepository, times(1)).addPlayer(gameId, user);
    }
}