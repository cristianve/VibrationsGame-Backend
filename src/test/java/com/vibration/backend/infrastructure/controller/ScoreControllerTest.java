package com.vibration.backend.infrastructure.controller;

import com.vibration.backend.application.dto.UserScoreResponseBody;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.infrastructure.repository.InMemoryScoreRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "/application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ScoreControllerTest {

    private static final User USER = new User("UserName");
    private static final Score SCORE = new Score(155);

    @Autowired
    private ScoreController scoreController;

    @Autowired
    private InMemoryScoreRepository inMemoryScoreRepository;

    @Test
    @Order(1)
    void getUserScore() {
        inMemoryScoreRepository.saveScore(USER, SCORE);

        UserScoreResponseBody userScore = scoreController.getUserScore(USER.getUserName());

        assertNotNull(userScore);
        assertEquals(USER.getUserName(), userScore.getUsername());
        assertEquals(SCORE.getPoints(), userScore.getScore());
    }

    @Test
    @Order(2)
    void getUserScoreFromUserNotExists() {
        UserScoreResponseBody userScore = scoreController.getUserScore("UserNotExists");

        inMemoryScoreRepository.deleteRanking();

        assertNotNull(userScore);
        assertEquals("UserNotExists", userScore.getUsername());
        assertEquals(0, userScore.getScore());
    }
}