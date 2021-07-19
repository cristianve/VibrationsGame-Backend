package com.vibration.backend.infrastructure.repository;

import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InMemoryScoreRepositoryTest {

    private final InMemoryScoreRepository inMemoryScoreRepository = new InMemoryScoreRepository();

    private final User user = new User("UserName");
    private final Score score = new Score(26);

    @Test
    @Order(1)
    void saveAndGetScore() throws GameException {
        inMemoryScoreRepository.saveScore(user, score);
        Score scoreRetrieved = inMemoryScoreRepository.getScore(user);

        assertNotNull(scoreRetrieved);
        assertEquals(score.getPoints(), scoreRetrieved.getPoints());
    }

    @Test
    @Order(2)
    void getScoreThrowsExceptionWhenUserNotFound() {
        assertThrows(GameException.class, () -> inMemoryScoreRepository.getScore(new User("User2")));
    }

    @Test
    @Order(3)
    void deleteRanking() {
        inMemoryScoreRepository.deleteRanking();

        assertThrows(GameException.class, () -> inMemoryScoreRepository.getScore(user));
    }
}