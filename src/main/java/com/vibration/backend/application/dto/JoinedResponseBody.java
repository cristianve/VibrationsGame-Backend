package com.vibration.backend.application.dto;

import java.io.Serializable;
import java.util.UUID;

public class JoinedResponseBody extends BasicResponseBody implements Serializable {

    private static final long serialVersionUID = 1980143444573860181L;

    private final UUID gameUuid;

    public JoinedResponseBody(int code, String message, String state, String action, UUID gameUuid) {
        super(code, message, state, action);
        this.gameUuid = gameUuid;
    }

    public UUID getGameUuid() {
        return gameUuid;
    }
}
