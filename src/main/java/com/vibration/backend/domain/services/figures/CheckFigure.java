package com.vibration.backend.domain.services.figures;

import com.vibration.backend.domain.FigureCheckerOperation;
import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.Figures;
import org.springframework.stereotype.Component;

import java.util.Queue;

@Component
public class CheckFigure {

    private final FigureCheckerOperation figureCheckerOperation;

    public CheckFigure(FigureCheckerOperation figureCheckerOperation) {
        this.figureCheckerOperation = figureCheckerOperation;
    }

    public boolean execute(Figures figure, Queue<CoordinatesData> coordinatesDataQueue) {
        return figureCheckerOperation.isCorrect(figure, coordinatesDataQueue);
    }
}
