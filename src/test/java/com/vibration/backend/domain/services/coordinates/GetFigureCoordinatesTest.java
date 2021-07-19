package com.vibration.backend.domain.services.coordinates;

import com.vibration.backend.domain.CoordinatesRepository;
import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetFigureCoordinatesTest {

    @Mock
    private CoordinatesRepository coordinatesRepository;

    @Mock
    private User user;

    @Mock
    private Queue<CoordinatesData> coordinatesDataQueue;

    @Test
    void execute() {
        when(coordinatesRepository.get(user)).thenReturn(coordinatesDataQueue);

        GetFigureCoordinates getFigureCoordinates = new GetFigureCoordinates(coordinatesRepository);
        Queue<CoordinatesData> coordinatesDataQueue = getFigureCoordinates.execute(user);

        assertNotNull(coordinatesDataQueue);
        assertEquals(this.coordinatesDataQueue, coordinatesDataQueue);
        verify(coordinatesRepository, times(1)).get(user);
    }
}