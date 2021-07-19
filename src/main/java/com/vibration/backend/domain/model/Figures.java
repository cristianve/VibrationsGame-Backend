package com.vibration.backend.domain.model;

import java.util.List;
import java.util.Random;

public enum Figures {

    MOVE_RIGHT,
    MOVE_LEFT,
    MOVE_UP,
    MOVE_DOWN,
    CIRCLE;

    private static final Random RANDOM = new Random();
    private static final List<Figures> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();

    public static Figures selectFigure() {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
