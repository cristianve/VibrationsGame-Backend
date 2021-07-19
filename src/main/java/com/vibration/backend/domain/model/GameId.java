package com.vibration.backend.domain.model;

import java.util.UUID;

public class GameId {

    private final UUID gameUuid;

    private GameId() {
        gameUuid = UUID.randomUUID();
    }

    public GameId(UUID gameUuid) {
        this.gameUuid = gameUuid;
    }

    public static GameId create() {
        return new GameId();
    }

    public UUID getGameUuid() {
        return gameUuid;
    }
}
