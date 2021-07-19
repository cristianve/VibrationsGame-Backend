package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.model.Figures;
import com.vibration.backend.domain.model.PlayUserData;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.services.coordinates.DeleteCoordinates;
import com.vibration.backend.domain.services.coordinates.GetFigureCoordinates;
import com.vibration.backend.domain.services.figures.CheckFigure;
import com.vibration.backend.domain.services.playuserdata.GetPlayUserData;
import com.vibration.backend.domain.services.socket.GetSocketUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewCoordinatesTest {

    @Mock
    private GetSocketAttributesOperations getSocketAttributesOperations;

    @Mock
    private GetFigureCoordinates getFigureCoordinates;

    @Mock
    private DeleteCoordinates deleteCoordinates;

    @Mock
    private CheckFigure checkFigure;

    @Mock
    private GetPlayUserData getPlayUserData;

    @Mock
    private GetSocketUser getSocketUser;

    @Mock
    private WebSocketSession session;

    @Mock
    private User user;

    @Mock
    private Queue<CoordinatesData> coordinatesDataQueue;

    @Mock
    private PlayUserData playUserData;

    @Test
    void execute() {
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);
        when(getFigureCoordinates.execute(user)).thenReturn(coordinatesDataQueue);
        doNothing().when(deleteCoordinates).execute(user);
        when(getPlayUserData.execute(user)).thenReturn(playUserData);
        when(playUserData.getFigure()).thenReturn(Figures.CIRCLE);
        when(checkFigure.execute(Figures.CIRCLE, coordinatesDataQueue)).thenReturn(Boolean.TRUE);

        ReviewCoordinates reviewCoordinates = new ReviewCoordinates(getSocketAttributesOperations, getFigureCoordinates,
                checkFigure, getPlayUserData, deleteCoordinates);
        boolean result = reviewCoordinates.execute(session);

        verify(checkFigure, times(1)).execute(Figures.CIRCLE, coordinatesDataQueue);
        assertTrue(result);
    }
}