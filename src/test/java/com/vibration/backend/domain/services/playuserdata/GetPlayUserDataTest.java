package com.vibration.backend.domain.services.playuserdata;

import com.vibration.backend.domain.PlayRepository;
import com.vibration.backend.domain.model.PlayUserData;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPlayUserDataTest {

    @Mock
    private PlayRepository playRepository;

    @Mock
    private User user;

    @Mock
    private PlayUserData playUserData;

    @Test
    void execute() {
        when(playRepository.get(user)).thenReturn(playUserData);

        GetPlayUserData getPlayUserData = new GetPlayUserData(playRepository);
        PlayUserData playUserData = getPlayUserData.execute(user);

        assertNotNull(playUserData);
        assertEquals(this.playUserData, playUserData);
        verify(playRepository, times(1)).get(user);
    }
}