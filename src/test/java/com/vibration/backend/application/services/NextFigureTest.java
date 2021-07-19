package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.model.Figures;
import com.vibration.backend.domain.model.PlayUserData;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.services.figures.SelectFigure;
import com.vibration.backend.domain.services.playuserdata.SavePlayUserData;
import com.vibration.backend.domain.services.socket.GetSocketUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NextFigureTest {

    @Mock
    private GetSocketAttributesOperations getSocketAttributesOperations;

    @Mock
    private SelectFigure selectFigure;

    @Mock
    private SavePlayUserData savePlayUserData;

    @Mock
    private GetSocketUser getSocketUser;

    @Mock
    private WebSocketSession session;

    @Mock
    private User user;

    @Test
    void execute() {
        when(selectFigure.execute()).thenReturn(Figures.CIRCLE);
        when(getSocketAttributesOperations.getGetSocketUser()).thenReturn(getSocketUser);
        when(getSocketUser.execute(session)).thenReturn(user);
        doNothing().when(savePlayUserData).execute(any(PlayUserData.class));

        NextFigure nextFigure = new NextFigure(getSocketAttributesOperations, selectFigure, savePlayUserData);
        Figures figures = nextFigure.execute(session);

        verify(savePlayUserData, times(1)).execute(any(PlayUserData.class));
        assertNotNull(figures);
        assertEquals(Figures.CIRCLE, figures);
    }
}