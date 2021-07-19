package com.vibration.backend.application.services;

import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.services.game.ListGames;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetListGamesTest {

    @Mock
    private ListGames listGames;

    @Test
    void execute() {
        List<Game> gameList = new ArrayList<>();
        when(listGames.execute()).thenReturn(gameList);

        GetListGames getListGames = new GetListGames(listGames);
        List<Game> games = getListGames.execute();

        assertNotNull(games);
        assertEquals(0, games.size());
    }
}