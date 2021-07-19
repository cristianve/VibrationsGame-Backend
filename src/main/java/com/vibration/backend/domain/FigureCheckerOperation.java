package com.vibration.backend.domain;

import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.Figures;

import java.util.Queue;

public interface FigureCheckerOperation {

    boolean isCorrect(Figures figure, Queue<CoordinatesData> coordinatesDataQueue);
}
