package com.vibration.backend.application.dto;

import java.io.Serializable;

public class ErrorResponseBody implements Serializable {

    private static final long serialVersionUID = 5493116643717909782L;

    private final int code;
    private final String message;

    public ErrorResponseBody(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
