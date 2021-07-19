package com.vibration.backend.infrastructure.controller;

import com.vibration.backend.application.dto.RankingRestResponseBody;
import com.vibration.backend.application.services.ShowRanking;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/ranking")
public class RankingController {

    private static final Logger log = LogManager.getLogger(RankingController.class);

    private final ShowRanking showRanking;

    public RankingController(ShowRanking showRanking) {
        this.showRanking = showRanking;
    }

    @GetMapping("/game/{gameId}")
    @ResponseBody
    public RankingRestResponseBody getRankingByGame(@PathVariable("gameId") UUID gameUUId) {
        Map<User, Score> scoreMap;
        try {
            scoreMap = showRanking.execute(gameUUId);
        } catch (GameException e) {
            log.warn("Error getting ranking due to {}", e.getMessage());

            scoreMap = new HashMap<>();
        }

        Map<String, Integer> ranking = scoreMap.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getUserName(),
                        e -> e.getValue().getPoints()));

        return new RankingRestResponseBody(ranking);
    }
}
