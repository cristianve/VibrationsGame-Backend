package com.vibration.backend.application.services;

import com.vibration.backend.application.dto.CoordinatesRequestBody;
import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.model.CoordinatesData;
import com.vibration.backend.domain.services.coordinates.SaveCoordinates;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class StoreCoordinates {

    private final GetSocketAttributesOperations getSocketAttributesOperations;
    private final SaveCoordinates saveCoordinates;

    public StoreCoordinates(GetSocketAttributesOperations getSocketAttributesOperations,
                            SaveCoordinates saveCoordinates) {
        this.getSocketAttributesOperations = getSocketAttributesOperations;
        this.saveCoordinates = saveCoordinates;
    }

    public void execute(WebSocketSession session, CoordinatesRequestBody requestBody) {
        var user = getSocketAttributesOperations.getGetSocketUser().execute(session);
        var coordinatesData = new CoordinatesData(requestBody.getTime(), requestBody.getxCoordinates(),
                requestBody.getyCoordinates(), requestBody.getzCoordinates());
        saveCoordinates.execute(user, coordinatesData);
    }
}
