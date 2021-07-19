package com.vibration.backend.domain.services.game;

import com.vibration.backend.domain.GameRepository;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListGamesTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private List<Game> gameList;

    @Test
    void execute() {
        when(gameRepository.getAllGames()).thenReturn(gameList);

        ListGames listGames = new ListGames(gameRepository);
        List<Game> gameList = listGames.execute();

        assertNotNull(gameList);
        assertEquals(this.gameList, gameList);
    }
}