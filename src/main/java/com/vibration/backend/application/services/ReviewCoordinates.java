package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.model.*;
import com.vibration.backend.domain.services.coordinates.DeleteCoordinates;
import com.vibration.backend.domain.services.coordinates.GetFigureCoordinates;
import com.vibration.backend.domain.services.figures.CheckFigure;
import com.vibration.backend.domain.services.playuserdata.GetPlayUserData;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Queue;

@Service
public class ReviewCoordinates {

    private final GetSocketAttributesOperations getSocketAttributesOperations;
    private final GetFigureCoordinates getFigureCoordinates;
    private final CheckFigure checkFigure;
    private final GetPlayUserData getPlayUserData;
    private final DeleteCoordinates deleteCoordinates;

    public ReviewCoordinates(GetSocketAttributesOperations getSocketAttributesOperations,
                             GetFigureCoordinates getFigureCoordinates,
                             CheckFigure checkFigure,
                             GetPlayUserData getPlayUserData, DeleteCoordinates deleteCoordinates) {
        this.getSocketAttributesOperations = getSocketAttributesOperations;
        this.getFigureCoordinates = getFigureCoordinates;
        this.checkFigure = checkFigure;
        this.getPlayUserData = getPlayUserData;
        this.deleteCoordinates = deleteCoordinates;
    }

    public boolean execute(WebSocketSession session) {
        var user = getSocketAttributesOperations.getGetSocketUser().execute(session);

        Queue<CoordinatesData> coordinatesDataQueue = getFigureCoordinates.execute(user);
        deleteCoordinates.execute(user);
        var playUserData = getPlayUserData.execute(user);
        return checkFigure.execute(playUserData.getFigure(), coordinatesDataQueue);
    }

}
