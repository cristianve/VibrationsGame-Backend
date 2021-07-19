package com.vibration.backend.domain.model;

import java.time.Duration;
import java.time.Instant;

public class PlayUserData {

    private final User user;
    private final Figures figure;
    private final Instant startFigureTime;

    public PlayUserData(User user, Figures figure, Instant startFigureTime) {
        this.user = user;
        this.figure = figure;
        this.startFigureTime = startFigureTime;
    }

    public String getUsername() {
        return user.getUserName();
    }

    public Figures getFigure() {
        return figure;
    }

    public Duration getFigureCompletionTime() {
        return Duration.between(startFigureTime, Instant.now());
    }
}
