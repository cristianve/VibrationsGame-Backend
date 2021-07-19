package com.vibration.backend.application.dto;

import java.io.Serializable;

public class BasicResponseBody implements Serializable {

    private static final long serialVersionUID = 6206875030727678387L;

    private final int code;
    private final String message;
    private final String state;
    private final String action;

    public BasicResponseBody(int code, String message, String state, String action) {
        this.code = code;
        this.message = message;
        this.state = state;
        this.action = action;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getState() {
        return state;
    }

    public String getAction() {
        return action;
    }
}
