package com.vibration.backend.application.dto;

import java.io.Serializable;

public class ReviewGameResponseBody extends BasicResponseBody implements Serializable {

    private static final long serialVersionUID = 5698575629757579770L;

    private final boolean correct;
    private final String figure;
    private final Integer score;

    public ReviewGameResponseBody(int code, String message, String state, String action, boolean correct, String figure, Integer score) {
        super(code, message, state, action);
        this.correct = correct;
        this.figure = figure;
        this.score = score;
    }

    public boolean isCorrect() {
        return correct;
    }

    public String getFigure() {
        return figure;
    }

    public Integer getScore() {
        return score;
    }
}
