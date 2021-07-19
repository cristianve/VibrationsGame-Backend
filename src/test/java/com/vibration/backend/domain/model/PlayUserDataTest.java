package com.vibration.backend.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayUserDataTest {

    private PlayUserData playUserData;

    @Mock
    private User user;

    @BeforeEach
    void before() {
        playUserData = new PlayUserData(user, Figures.CIRCLE, Instant.now());
    }

    @Test
    void getUsername() {
        when(user.getUserName()).thenReturn("username");

        String username = playUserData.getUsername();

        assertEquals("username", username);
    }

    @Test
    void getFigure() {
        Figures figure = playUserData.getFigure();

        assertEquals(Figures.CIRCLE, figure);
    }

    @Test
    void getFigureCompletionTime() {
        Duration duration = playUserData.getFigureCompletionTime();

        assertNotNull(duration);
        assertFalse(duration.isNegative());
    }
}