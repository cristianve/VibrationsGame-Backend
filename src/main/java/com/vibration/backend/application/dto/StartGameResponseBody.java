package com.vibration.backend.application.dto;

import java.io.Serializable;

public class StartGameResponseBody extends BasicResponseBody implements Serializable {

    private static final long serialVersionUID = -1354045081718516358L;

    private final String figure;

    public StartGameResponseBody(int code, String message, String state, String action, String figure) {
        super(code, message, state, action);
        this.figure = figure;
    }

    public String getFigure() {
        return figure;
    }
}
