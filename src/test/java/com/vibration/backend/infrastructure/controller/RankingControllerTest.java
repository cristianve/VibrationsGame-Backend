package com.vibration.backend.infrastructure.controller;

import com.vibration.backend.application.dto.RankingRestResponseBody;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Game;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.infrastructure.repository.InMemoryGameRepository;
import com.vibration.backend.infrastructure.repository.InMemoryScoreRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "/application.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class RankingControllerTest {

    private static final User USER_1 = new User("UserName1");
    private static final Score SCORE_1 = new Score(75);
    private static final User USER_2 = new User("UserName2");
    private static final Score SCORE_2 = new Score(150);
    private static final User USER_3 = new User("UserName3");
    private static final Score SCORE_3 = new Score(30);

    @Autowired
    private RankingController rankingController;

    @Autowired
    private InMemoryGameRepository inMemoryGameRepository;

    @Autowired
    private InMemoryScoreRepository inMemoryScoreRepository;

    @Test
    @Order(1)
    void getRankingByGame() throws GameException {
        Game game = new Game("Test");
        game.setPlaying(true);
        game.addPlayer(USER_1);
        game.addPlayer(USER_2);
        game.addPlayer(USER_3);

        GameId gameId = inMemoryGameRepository.saveGame(game);

        inMemoryScoreRepository.saveScore(USER_1, SCORE_1);
        inMemoryScoreRepository.saveScore(USER_2, SCORE_2);
        inMemoryScoreRepository.saveScore(USER_3, SCORE_3);

        RankingRestResponseBody rankingByGame = rankingController.getRankingByGame(gameId.getGameUuid());

        inMemoryScoreRepository.deleteRanking();
        inMemoryGameRepository.deleteGame(gameId);

        assertNotNull(rankingByGame);
        assertNotNull(rankingByGame.getRanking());
        assertEquals(3, rankingByGame.getRanking().size());
    }

    @Test
    @Order(2)
    void getRankingWhenGameNotExists() {
        RankingRestResponseBody rankingByGame = rankingController.getRankingByGame(UUID.randomUUID());

        assertNotNull(rankingByGame);
        assertNotNull(rankingByGame.getRanking());
        assertEquals(0, rankingByGame.getRanking().size());
    }
}