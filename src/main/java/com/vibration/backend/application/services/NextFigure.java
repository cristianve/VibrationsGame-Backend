package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.model.Figures;
import com.vibration.backend.domain.model.PlayUserData;
import com.vibration.backend.domain.services.figures.SelectFigure;
import com.vibration.backend.domain.services.playuserdata.SavePlayUserData;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.time.Instant;

@Service
public class NextFigure {

    private final GetSocketAttributesOperations getSocketAttributesOperations;
    private final SelectFigure selectFigure;
    private final SavePlayUserData savePlayUserData;

    public NextFigure(GetSocketAttributesOperations getSocketAttributesOperations,
                      SelectFigure selectFigure,
                      SavePlayUserData savePlayUserData) {
        this.getSocketAttributesOperations = getSocketAttributesOperations;
        this.selectFigure = selectFigure;
        this.savePlayUserData = savePlayUserData;
    }

    public Figures execute(WebSocketSession session) {
        Figures figure = selectFigure.execute();

        var user = getSocketAttributesOperations.getGetSocketUser().execute(session);
        var playUserData = new PlayUserData(user, figure, Instant.now());
        savePlayUserData.execute(playUserData);

        return figure;
    }
}
