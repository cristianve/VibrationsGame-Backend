package com.vibration.backend.domain.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameIdTest {

    @Test
    void create() {
        GameId gameId = GameId.create();

        assertNotNull(gameId);
        assertNotNull(gameId.getGameUuid());
    }

    @Test
    void getGameUuid() {
        UUID uuid = UUID.randomUUID();
        GameId gameId = new GameId(uuid);

        assertNotNull(gameId);
        assertEquals(uuid, gameId.getGameUuid());
    }
}