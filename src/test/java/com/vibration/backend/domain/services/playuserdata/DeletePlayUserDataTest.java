package com.vibration.backend.domain.services.playuserdata;

import com.vibration.backend.domain.PlayRepository;
import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePlayUserDataTest {

    @Mock
    private PlayRepository playRepository;

    @Mock
    private User user;

    @Test
    void execute() {
        doNothing().when(playRepository).delete(user);

        DeletePlayUserData deletePlayUserData = new DeletePlayUserData(playRepository);
        deletePlayUserData.execute(user);

        verify(playRepository, times(1)).delete(user);
    }
}