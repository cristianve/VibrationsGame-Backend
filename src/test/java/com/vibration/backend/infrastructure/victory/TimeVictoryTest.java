package com.vibration.backend.infrastructure.victory;

import com.vibration.backend.application.dto.BasicResponseBody;
import com.vibration.backend.domain.exceptions.GameException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TimeVictoryTest {

    @Mock
    private FinishGameCallable finishGameCallable;

    @Test
    void create() throws GameException {
        when(finishGameCallable.call()).thenReturn(new HashMap<>());

        TimeVictory timeVictory = new TimeVictory();
        Map<WebSocketSession, BasicResponseBody> responseMap = timeVictory.create(finishGameCallable, 1000);

        verify(finishGameCallable, times(1)).call();
        assertNotNull(responseMap);
        assertEquals(0, responseMap.size());
    }
}