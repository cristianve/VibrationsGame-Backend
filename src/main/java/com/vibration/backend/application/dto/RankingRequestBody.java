package com.vibration.backend.application.dto;

import java.io.Serializable;
import java.util.UUID;

public class RankingRequestBody implements Serializable {

    private static final long serialVersionUID = -2640245640816526623L;

    private final UUID gameUuid;

    public RankingRequestBody(UUID gameUuid) {
        this.gameUuid = gameUuid;
    }

    public UUID getGameUuid() {
        return gameUuid;
    }
}
