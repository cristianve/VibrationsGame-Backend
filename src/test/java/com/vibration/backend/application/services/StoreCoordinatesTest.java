package com.vibration.backend.application.services;

import com.vibration.backend.application.dto.CoordinatesRequestBody;
import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.services.coordinates.SaveCoordinates;
import com.vibration.backend.domain.services.socket.GetSocketUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StoreCoordinatesTest {

    @Mock
    private GetSocketAttributesOperations getSocketAttributesOperations;

    @Mock
    private SaveCoordinates saveCoordinates;

    @Mock
    private GetSocketUser getSocketUser;

    @Mock
    private User user;

    @Mock
    private WebSocketSession session;

    @Mock
    private CoordinatesRequestBody requestBody;

    @Test
    void execute() {
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);
        when(requestBody.getxCoordinates()).thenReturn(10.1f);
        when(requestBody.getyCoordinates()).thenReturn(10.1f);
        doNothing().when(saveCoordinates).execute(eq(user), any(CoordinatesData.class));

        StoreCoordinates storeCoordinates = new StoreCoordinates(getSocketAttributesOperations, saveCoordinates);
        storeCoordinates.execute(session, requestBody);

        verify(saveCoordinates, times(1)).execute(eq(user), any(CoordinatesData.class));
    }
}