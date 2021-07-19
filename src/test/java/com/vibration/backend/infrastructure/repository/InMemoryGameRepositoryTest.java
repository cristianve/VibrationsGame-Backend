package com.vibration.backend.infrastructure.repository;

import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InMemoryGameRepositoryTest {

    private final InMemoryGameRepository gameRepository = new InMemoryGameRepository();

    private static Game game;
    private static Game game2;
    private static final User user = new User("UserName");

    @Test
    @Order(1)
    void saveGame() {
        game = new Game("Name");
        GameId gameId = gameRepository.saveGame(game);

        assertNotNull(gameId);
        assertEquals(game.getGameUuid(), gameId.getGameUuid());
    }

    @Test
    @Order(2)
    void checkGameExists() {
        boolean exists = gameRepository.checkGameNameExists(game.getName());

        assertTrue(exists);
    }

    @Test
    @Order(3)
    void getGame() throws GameException {
        Game game = gameRepository.getGame(InMemoryGameRepositoryTest.game.getId());

        assertEquals(InMemoryGameRepositoryTest.game, game);
    }

    @Test
    @Order(4)
    void getGameThrowsExceptionWhenGameDoesNotExists() {
        assertThrows(GameException.class, () -> gameRepository.getGame(new GameId(UUID.randomUUID())));
    }

    @Test
    @Order(5)
    void addPlayerOK() {
        assertDoesNotThrow(() -> gameRepository.addPlayer(InMemoryGameRepositoryTest.game.getId(), user));
    }

    @Test
    @Order(6)
    void addPlayerThrowsExceptionBecauseGameNotExists() {
        assertThrows(GameException.class, () -> gameRepository.addPlayer(new GameId(UUID.randomUUID()), user));
    }

    @Test
    @Order(7)
    void getPlayersInGame() throws GameException {
        List<User> playersInGame = gameRepository.getPlayersInGame(InMemoryGameRepositoryTest.game.getId());

        assertNotNull(playersInGame);
        assertEquals(1, playersInGame.size());
    }

    @Test
    @Order(8)
    void getPlayersInGameThrowsExceptionWhenGameNotExists() {
        assertThrows(GameException.class, () -> gameRepository.getPlayersInGame(new GameId(UUID.randomUUID())));
    }

    @Test
    @Order(9)
    void removePlayerOK() {
        assertDoesNotThrow(() -> gameRepository.removePlayer(InMemoryGameRepositoryTest.game.getId(), user));
    }

    @Test
    @Order(10)
    void removePlayerThrowsExceptionBecauseGameNotExists() {
        assertThrows(GameException.class, () -> gameRepository.removePlayer(new GameId(UUID.randomUUID()), user));
    }

    @Test
    @Order(11)
    void getAllGames() {
        game2 = new Game("Name2");
        gameRepository.saveGame(game2);

        List<Game> allGames = gameRepository.getAllGames();

        assertNotNull(allGames);
        assertEquals(2, allGames.size());
    }

    @Test
    @Order(12)
    void deleteGame() {
        gameRepository.deleteGame(InMemoryGameRepositoryTest.game.getId());
        gameRepository.deleteGame(InMemoryGameRepositoryTest.game2.getId());

        assertFalse(gameRepository.checkGameNameExists(InMemoryGameRepositoryTest.game.getName()));
        assertFalse(gameRepository.checkGameNameExists(InMemoryGameRepositoryTest.game2.getName()));
    }


}