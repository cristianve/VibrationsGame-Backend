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
class RemovePlayerFromGameTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameId gameId;

    @Mock
    private User user;

    @Test
    void executeShouldRemovePlayerFromGame() throws GameException {
        doNothing().when(gameRepository).removePlayer(gameId, user);

        RemovePlayerFromGame removePlayerFromGame = new RemovePlayerFromGame(gameRepository);
        assertDoesNotThrow(() -> removePlayerFromGame.execute(gameId, user));

        verify(gameRepository, times(1)).removePlayer(gameId, user);
    }

    @Test
    void executeShouldThrowGameException() throws GameException {
        doThrow(GameException.class).when(gameRepository).removePlayer(gameId, user);

        RemovePlayerFromGame removePlayerFromGame = new RemovePlayerFromGame(gameRepository);
        assertThrows(GameException.class, () -> removePlayerFromGame.execute(gameId, user));

        verify(gameRepository, times(1)).removePlayer(gameId, user);
    }
}