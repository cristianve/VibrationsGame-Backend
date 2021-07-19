package com.vibration.backend.domain.services.figures;

import com.vibration.backend.domain.FigureCheckerOperation;
import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.Figures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckFigureTest {

    @Mock
    private FigureCheckerOperation figureCheckerOperation;

    @Mock
    private Queue<CoordinatesData> coordinatesDataQueue;

    @Test
    void executeShouldReturnTrue() {
        when(figureCheckerOperation.isCorrect(Figures.CIRCLE, coordinatesDataQueue)).thenReturn(Boolean.TRUE);

        CheckFigure checkFigure = new CheckFigure(figureCheckerOperation);
        boolean isValidFigure = checkFigure.execute(Figures.CIRCLE, coordinatesDataQueue);

        assertTrue(isValidFigure);
        verify(figureCheckerOperation, times(1)).isCorrect(Figures.CIRCLE, coordinatesDataQueue);
    }

    @Test
    void executeShouldReturnFalse() {
        when(figureCheckerOperation.isCorrect(Figures.CIRCLE, coordinatesDataQueue)).thenReturn(Boolean.FALSE);

        CheckFigure checkFigure = new CheckFigure(figureCheckerOperation);
        boolean isValidFigure = checkFigure.execute(Figures.CIRCLE, coordinatesDataQueue);

        assertFalse(isValidFigure);
        verify(figureCheckerOperation, times(1)).isCorrect(Figures.CIRCLE, coordinatesDataQueue);
    }
}