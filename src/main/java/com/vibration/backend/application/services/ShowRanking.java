package com.vibration.backend.application.services;

import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.services.game.ListPlayersInGame;
import com.vibration.backend.domain.services.score.GetScore;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ShowRanking {

    private final ListPlayersInGame listPlayersInGame;
    private final GetScore getScore;

    public ShowRanking(ListPlayersInGame listPlayersInGame, GetScore getScore) {
        this.listPlayersInGame = listPlayersInGame;
        this.getScore = getScore;
    }

    public Map<User, Score> execute(UUID gameUuid) throws GameException {
        Map<User, Score> userScoreMap = new ConcurrentHashMap<>();

        var gameId = new GameId(gameUuid);
        List<User> userList = listPlayersInGame.execute(gameId);
        userList.parallelStream().forEach(user -> {
            Score score;
            try {
                score = getScore.execute(user);
            } catch (GameException e) {
                score = new Score(0);
            }
            userScoreMap.put(user, score);
        });

        List<Map.Entry<User, Score>> listScores = new ArrayList<>(userScoreMap.entrySet());
        listScores.sort(Map.Entry.comparingByValue(Comparator.comparing(Score::getPoints).reversed()));

        Map<User, Score> ranking = new LinkedHashMap<>();
        listScores.forEach(userScore -> ranking.put(userScore.getKey(), userScore.getValue()));

        return ranking;
    }
}
