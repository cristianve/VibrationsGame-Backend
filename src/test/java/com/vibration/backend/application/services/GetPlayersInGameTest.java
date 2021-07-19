package com.vibration.backend.application.services;

import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.services.game.ListPlayersInGame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPlayersInGameTest {

    @Mock
    private ListPlayersInGame listPlayersInGame;

    @Test
    void executeThrowsGameException() throws GameException {
        when(listPlayersInGame.execute(any(GameId.class))).thenThrow(GameException.class);

        GetPlayersInGame getPlayersInGame = new GetPlayersInGame(listPlayersInGame);
        assertThrows(GameException.class, () -> getPlayersInGame.execute(UUID.randomUUID()));
    }

    @Test
    void executeOK() throws GameException {
        List<User> userList = new ArrayList<>();
        when(listPlayersInGame.execute(any(GameId.class))).thenReturn(userList);

        GetPlayersInGame getPlayersInGame = new GetPlayersInGame(listPlayersInGame);
        List<User> users = getPlayersInGame.execute(UUID.randomUUID());

        assertNotNull(users);
        assertEquals(0, users.size());
    }
}