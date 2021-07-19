package com.vibration.backend.infrastructure.tensorflow;

import com.vibration.backend.domain.FigureCheckerOperation;
import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.Figures;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.Random;

@Component
@Profile("mockTensorflow")
public class MockTensorflowOperation implements FigureCheckerOperation {

    private static final Random RAND = new Random();

    @Override
    public boolean isCorrect(Figures figure, Queue<CoordinatesData> coordinatesDataQueue) {
        return RAND.nextBoolean();
    }
}
