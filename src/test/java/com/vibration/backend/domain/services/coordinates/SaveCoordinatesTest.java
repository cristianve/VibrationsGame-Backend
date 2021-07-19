package com.vibration.backend.domain.services.coordinates;

import com.vibration.backend.domain.CoordinatesRepository;
import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaveCoordinatesTest {

    @Mock
    private CoordinatesRepository coordinatesRepository;

    @Mock
    private User user;

    @Mock
    private CoordinatesData coordinatesData;

    @Test
    void execute() {
        doNothing().when(coordinatesRepository).save(user, coordinatesData);

        SaveCoordinates saveCoordinates = new SaveCoordinates(coordinatesRepository);
        saveCoordinates.execute(user, coordinatesData);

        verify(coordinatesRepository, times(1)).save(user, coordinatesData);
    }
}