package com.vibration.backend.domain.model;

import com.vibration.backend.domain.exceptions.GameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void createDefaultConstructor() {
        Game game = new Game("Name");

        assertAll("Should create default Game",
                () -> assertNotNull(game),
                () -> assertNotNull(game.getId()),
                () -> assertEquals("Name", game.getName()),
                () -> assertFalse(game.isPlaying()),
                () -> assertEquals(10, game.getMaxPlayers()),
                () -> assertEquals(0, game.getCurrentPlayers().size()),
                () -> assertEquals(VictoryConditions.TIME, game.getVictoryCondition()),
                () -> assertEquals(60000, game.getCondition())
        );
    }

    @Test
    void createFullConstructor() {
        Game game = new Game("Name2", 25, VictoryConditions.POINTS, 1000);

        assertAll("Should create Game with defined parameters",
                () -> assertNotNull(game),
                () -> assertNotNull(game.getId()),
                () -> assertEquals("Name2", game.getName()),
                () -> assertFalse(game.isPlaying()),
                () -> assertEquals(25, game.getMaxPlayers()),
                () -> assertEquals(0, game.getCurrentPlayers().size()),
                () -> assertEquals(VictoryConditions.POINTS, game.getVictoryCondition()),
                () -> assertEquals(1000, game.getCondition())
        );
    }

    @Test
    void addPlayer() throws GameException {
        Game game = new Game("Name3");
        game.addPlayer(new User("UserName"));

        assertEquals(1, game.getCurrentPlayers().size());
    }

    @Test
    void addPlayerThrowsGameException() throws GameException {
        Game game = new Game("Name4", 1, VictoryConditions.POINTS, 1000);
        game.addPlayer(new User("UserName"));

        assertThrows(GameException.class, () -> game.addPlayer(new User("UserName2")));
    }

    @Test
    void removePlayer() throws GameException {
        Game game = new Game("Name5");
        game.addPlayer(new User("UserName"));

        assertDoesNotThrow(() -> game.removePlayer(new User("UserName")));
        assertEquals(0, game.getCurrentPlayers().size());
    }

    @Test
    void removePlayerThrowsGameException() throws GameException {
        Game game = new Game("Name6");
        game.addPlayer(new User("UserName"));

        assertThrows(GameException.class, () -> game.removePlayer(new User("UserName2")));
    }
}