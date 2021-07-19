package com.vibration.backend.domain.services.score;

import com.vibration.backend.domain.model.PlayUserData;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculateScoreTest {

    @Mock
    private PlayUserData playUserData;

    private static Stream<Arguments> provideDataForTest() {
        return Stream.of(
                Arguments.of(1824, 100),
                Arguments.of(4589, 75),
                Arguments.of(12523, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDataForTest")
    void executeShouldReturnMaxPoints(long duration, Integer pointsExpected) {
        when(playUserData.getFigureCompletionTime()).thenReturn(Duration.ofMillis(duration));

        CalculateScore calculateScore = new CalculateScore();
        Integer points = calculateScore.execute(playUserData);

        assertNotNull(points);
        assertEquals(pointsExpected, points);
    }
}