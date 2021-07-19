package com.vibration.backend.domain.services.coordinates;

import com.vibration.backend.domain.CoordinatesRepository;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteCoordinatesTest {

    @Mock
    private CoordinatesRepository coordinatesRepository;

    @Mock
    private User user;

    @Test
    void execute() {
        doNothing().when(coordinatesRepository).delete(user);

        DeleteCoordinates deleteCoordinates = new DeleteCoordinates(coordinatesRepository);
        deleteCoordinates.execute(user);

        verify(coordinatesRepository, times(1)).delete(user);
    }
}