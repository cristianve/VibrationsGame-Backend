package com.vibration.backend.domain.services.playuserdata;

import com.vibration.backend.domain.PlayRepository;
import com.vibration.backend.domain.model.PlayUserData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SavePlayUserDataTest {

    @Mock
    private PlayRepository playRepository;

    @Mock
    private PlayUserData playUserData;

    @Test
    void execute() {
        doNothing().when(playRepository).save(playUserData);

        SavePlayUserData savePlayUserData = new SavePlayUserData(playRepository);
        savePlayUserData.execute(playUserData);

        verify(playRepository, times(1)).save(playUserData);
    }
}