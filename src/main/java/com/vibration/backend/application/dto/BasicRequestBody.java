package com.vibration.backend.application.dto;

import com.vibration.backend.domain.model.SocketAction;

import java.io.Serializable;

public class BasicRequestBody implements Serializable {

    private static final long serialVersionUID = -7854479675228756849L;

    private final SocketAction action;

    public BasicRequestBody(SocketAction action) {
        this.action = action;
    }

    public SocketAction getAction() {
        return action;
    }
}
