package com.vibration.backend.domain.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class FiguresTest {

    @Test
    void selectFigure() {
        Figures selected = Figures.selectFigure();

        assertNotNull(selected);
    }

    @Test
    void values() {
        Figures[] values = Figures.values();

        assertNotNull(values);
        assertEquals(5, values.length);
    }

    @ParameterizedTest
    @ValueSource(strings = {"MOVE_RIGHT", "MOVE_LEFT", "MOVE_UP", "MOVE_DOWN", "CIRCLE"})
    void valueOf(String figureString) {
        Figures figure = Figures.valueOf(figureString);

        assertEquals(figureString, figure.toString());
    }

    @ParameterizedTest
    @EmptySource
    void valueOfThrowsException(String figureString) {
        assertThrows(IllegalArgumentException.class, () -> Figures.valueOf(figureString));
    }
}