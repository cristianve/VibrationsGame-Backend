package com.vibration.backend.infrastructure.controller;

import com.vibration.backend.application.dto.GameListResponse;
import com.vibration.backend.application.dto.PlayerListResponse;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.infrastructure.repository.InMemoryGameRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "/application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class GameControllerTest {

    private static final Game GAME_1 = new Game("Game1");
    private static final Game GAME_2 = new Game("Game2");
    private static final User USER_1 = new User("UserName1");
    private static final User USER_2 = new User("UserName2");
    private static final User USER_3 = new User("UserName3");

    private static GameId game1Id;

    @Autowired
    private GameController gameController;

    @Autowired
    private InMemoryGameRepository inMemoryGameRepository;

    @Test
    @Order(1)
    void getGameListWhenNoGameCreated() {
        GameListResponse gameList = gameController.getGameList();

        assertNotNull(gameList);
        assertNotNull(gameList.getGameList());
        assertEquals(0, gameList.getGameList().size());
        assertEquals(0, gameList.getNumber());
    }

    @Test
    @Order(2)
    void getGameList() {
        game1Id = inMemoryGameRepository.saveGame(GAME_1);
        GameId game2Id = inMemoryGameRepository.saveGame(GAME_2);

        GameListResponse gameList = gameController.getGameList();

        inMemoryGameRepository.deleteGame(game2Id);

        assertNotNull(gameList);
        assertNotNull(gameList.getGameList());
        assertEquals(2, gameList.getGameList().size());
        assertEquals(2, gameList.getNumber());
    }

    @Test
    @Order(3)
    void getPlayersInGame() throws GameException {
        GAME_1.addPlayer(USER_1);
        GAME_1.addPlayer(USER_2);
        GAME_1.addPlayer(USER_3);

        inMemoryGameRepository.saveGame(GAME_1);

        PlayerListResponse playersInGame = gameController.getPlayersInGame(game1Id.getGameUuid());

        inMemoryGameRepository.deleteGame(game1Id);

        assertNotNull(playersInGame);
        assertNotNull(playersInGame.getPlayerList());
        assertEquals(3, playersInGame.getPlayerList().size());
        assertEquals(3, playersInGame.getNumber());
    }

    @Test
    @Order(4)
    void getPlayersInGameWhenGameNotExists() {
        PlayerListResponse playersInGame = gameController.getPlayersInGame(UUID.randomUUID());

        assertNotNull(playersInGame);
        assertNotNull(playersInGame.getPlayerList());
        assertEquals(0, playersInGame.getPlayerList().size());
        assertEquals(0, playersInGame.getNumber());
    }

}