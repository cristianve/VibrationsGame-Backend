package com.vibration.backend.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreTest {

    @Test
    void createNullScore() {
        Score score = new Score(null);

        assertNotNull(score);
        assertEquals(0, score.getPoints());
    }

    @Test
    void createNegativeScore() {
        Score score = new Score(-123);

        assertNotNull(score);
        assertEquals(0, score.getPoints());
    }

    @Test
    void getPoints() {
        Score score = new Score(123);

        assertNotNull(score);
        assertEquals(123, score.getPoints());
    }

    @Test
    void increaseScore() {
        Score score = new Score(123);
        score = score.increaseScore(321);

        assertNotNull(score);
        assertEquals(444, score.getPoints());
    }
}