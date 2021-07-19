package com.vibration.backend.application.dto;

import java.io.Serializable;

public class UserScoreResponseBody implements Serializable {

    private static final long serialVersionUID = -2857875695010604500L;

    private final String username;
    private final Integer score;

    public UserScoreResponseBody(String username, Integer score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public Integer getScore() {
        return score;
    }
}
