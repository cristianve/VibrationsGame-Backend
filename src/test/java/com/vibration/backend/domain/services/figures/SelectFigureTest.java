package com.vibration.backend.domain.services.figures;

import com.vibration.backend.domain.model.Figures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectFigureTest {

    @Test
    void execute() {
        SelectFigure selectFigure = new SelectFigure();
        Figures selectedFigure = selectFigure.execute();

        assertNotNull(selectedFigure);
    }
}