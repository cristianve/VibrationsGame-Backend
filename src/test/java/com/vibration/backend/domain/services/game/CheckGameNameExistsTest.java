package com.vibration.backend.domain.services.game;

import com.vibration.backend.domain.GameRepository;
import com.vibration.backend.domain.model.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckGameNameExistsTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private Game game;

    @Test
    void executeReturnsTrue() {
        when(game.getName()).thenReturn("Name");
        when(gameRepository.checkGameNameExists("Name")).thenReturn(Boolean.TRUE);

        CheckGameNameExists checkGameNameExists = new CheckGameNameExists(gameRepository);
        boolean exists = checkGameNameExists.execute(game);

        assertTrue(exists);
    }

    @Test
    void executeReturnsFalse() {
        when(game.getName()).thenReturn("Name");
        when(gameRepository.checkGameNameExists("Name")).thenReturn(Boolean.FALSE);

        CheckGameNameExists checkGameNameExists = new CheckGameNameExists(gameRepository);
        boolean exists = checkGameNameExists.execute(game);

        assertFalse(exists);
    }
}