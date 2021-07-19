package com.vibration.backend.domain.services.game;

import com.vibration.backend.domain.GameRepository;
import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.model.GameId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveGameTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private Game game;

    @Mock
    private GameId gameId;

    @Test
    void execute() {
        when(gameRepository.saveGame(game)).thenReturn(gameId);

        SaveGame saveGame = new SaveGame(gameRepository);
        GameId gameId = saveGame.execute(game);

        assertNotNull(gameId);
        assertEquals(this.gameId, gameId);
        verify(gameRepository, times(1)).saveGame(game);
    }
}