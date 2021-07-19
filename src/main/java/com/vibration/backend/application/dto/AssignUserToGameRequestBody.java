package com.vibration.backend.application.dto;

import com.vibration.backend.domain.model.SocketAction;

import java.io.Serializable;
import java.util.UUID;

public class AssignUserToGameRequestBody extends BasicRequestBody implements Serializable {

    private static final long serialVersionUID = -8692771553065729305L;

    private final UUID gameId;

    public AssignUserToGameRequestBody(SocketAction action, UUID gameId) {
        super(action);
        this.gameId = gameId;
    }

    public UUID getGameId() {
        return gameId;
    }
}
