package com.vibration.backend.domain.services.game;

import com.vibration.backend.domain.GameRepository;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListPlayersInGameTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private GameId gameId;

    @Mock
    private List<User> userList;

    @Test
    void executeThrowsGameException() throws GameException {
        when(gameRepository.getPlayersInGame(gameId)).thenThrow(GameException.class);

        ListPlayersInGame listPlayersInGame = new ListPlayersInGame(gameRepository);
        assertThrows(GameException.class, () -> listPlayersInGame.execute(gameId));
    }

    @Test
    void execute() throws GameException {
        when(gameRepository.getPlayersInGame(gameId)).thenReturn(userList);

        ListPlayersInGame listPlayersInGame = new ListPlayersInGame(gameRepository);
        List<User> userList = listPlayersInGame.execute(gameId);

        assertNotNull(userList);
        assertEquals(this.userList, userList);
    }
}