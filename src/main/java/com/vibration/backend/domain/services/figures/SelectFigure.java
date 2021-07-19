package com.vibration.backend.domain.services.figures;

import com.vibration.backend.domain.model.Figures;
import org.springframework.stereotype.Component;

@Component
public class SelectFigure {

    public Figures execute() {
        return Figures.selectFigure();
    }
}
