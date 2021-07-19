package com.vibration.backend.application.services;

import com.vibration.backend.domain.exceptions.GameException;
import com.vibration.backend.domain.model.GameId;
import com.vibration.backend.domain.model.Score;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.services.game.ListPlayersInGame;
import com.vibration.backend.domain.services.score.GetScore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowRankingTest {

    @Mock
    private ListPlayersInGame listPlayersInGame;

    @Mock
    private GetScore getScore;

    @Mock
    private User user1;

    @Mock
    private Score score;

    @Test
    void execute() throws GameException {
        UUID uuid = UUID.randomUUID();
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        when(listPlayersInGame.execute(any(GameId.class))).thenReturn(userList);
        when(getScore.execute(user1)).thenReturn(score);

        ShowRanking showRanking = new ShowRanking(listPlayersInGame, getScore);
        Map<User, Score> scoreMap = showRanking.execute(uuid);

        assertNotNull(scoreMap);
        assertEquals(1, scoreMap.size());
    }
}