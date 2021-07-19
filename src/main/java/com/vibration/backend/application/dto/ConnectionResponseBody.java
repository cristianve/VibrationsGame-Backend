package com.vibration.backend.application.dto;

import java.io.Serializable;

public class ConnectionResponseBody extends BasicResponseBody implements Serializable {

    private static final long serialVersionUID = 7920066704045413983L;

    private final String username;

    public ConnectionResponseBody(int code, String message, String state, String action, String username) {
        super(code, message, state, action);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
