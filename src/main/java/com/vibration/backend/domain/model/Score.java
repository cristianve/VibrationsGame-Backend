package com.vibration.backend.domain.model;

public class Score {

    private final Integer points;

    public Score(Integer points) {
        if (points == null || points < 0) {
            points = 0;
        }
        this.points = points;
    }

    public Integer getPoints() {
        return points;
    }

    public Score increaseScore(Integer increment) {
        return new Score(points + increment);
    }
}
