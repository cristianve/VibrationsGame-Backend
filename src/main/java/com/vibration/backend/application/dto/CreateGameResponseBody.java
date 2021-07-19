package com.vibration.backend.application.dto;

import java.io.Serializable;
import java.util.UUID;

public class CreateGameResponseBody extends BasicResponseBody implements Serializable {

    private static final long serialVersionUID = 1683639607508193011L;

    private final UUID uuid;

    public CreateGameResponseBody(int code, String message, String state, String action, UUID uuid) {
        super(code, message, state, action);
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }
}
