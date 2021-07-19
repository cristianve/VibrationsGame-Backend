package com.vibration.backend.domain.services.game;

import com.vibration.backend.domain.GameRepository;
import com.vibration.backend.domain.model.GameId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteGameTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameId gameId;

    @Test
    void execute() {
        doNothing().when(gameRepository).deleteGame(gameId);

        DeleteGame deleteGame = new DeleteGame(gameRepository);
        deleteGame.execute(gameId);

        verify(gameRepository, times(1)).deleteGame(gameId);
    }
}