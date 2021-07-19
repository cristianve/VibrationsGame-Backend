package com.vibration.backend.domain.services.ranking;

import com.vibration.backend.domain.ScoreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRankingTest {

    @Mock
    private ScoreRepository scoreRepository;

    @Test
    void execute() {
        doNothing().when(scoreRepository).deleteRanking();

        DeleteRanking deleteRanking = new DeleteRanking(scoreRepository);
        deleteRanking.execute();

        verify(scoreRepository, times(1)).deleteRanking();
    }
}