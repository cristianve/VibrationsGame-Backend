package com.vibration.backend.infrastructure.controller;

import com.vibration.backend.application.dto.UserScoreResponseBody;
import com.vibration.backend.application.services.GetUserScore;
import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.Score;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/score")
public class ScoreController {

    private static final Logger log = LogManager.getLogger(ScoreController.class);

    private final GetUserScore getUserScore;

    public ScoreController(GetUserScore getUserScore) {
        this.getUserScore = getUserScore;
    }

    @GetMapping("/user/{userId}")
    @ResponseBody
    public UserScoreResponseBody getUserScore(@PathVariable("userId") String username) {
        Score score;
        try {
            score = getUserScore.execute(username);
        } catch (GameException e) {
            log.warn("Error getting user score due to {}", e.getMessage());
            score = new Score(0);
        }
        return new UserScoreResponseBody(username, score.getPoints());
    }
}
