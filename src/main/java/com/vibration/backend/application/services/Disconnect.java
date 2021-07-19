package com.vibration.backend.application.services;

import com.vibration.backend.application.models.DeleteDataOperations;
import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.services.session.GetSocketSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

@Service
public class Disconnect {

    private static final Logger log = LogManager.getLogger(Disconnect.class);

    private final GetSocketSession getSocketSession;
    private final DeleteDataOperations deleteDataOperations;
    private final GetSocketAttributesOperations getSocketAttributesOperations;

    public Disconnect(GetSocketSession getSocketSession,
                      DeleteDataOperations deleteDataOperations,
                      GetSocketAttributesOperations getSocketAttributesOperations) {
        this.getSocketSession = getSocketSession;
        this.deleteDataOperations = deleteDataOperations;
        this.getSocketAttributesOperations = getSocketAttributesOperations;
    }

    public void execute(WebSocketSession session) {
        var user = getSocketAttributesOperations.getGetSocketUser().execute(session);
        WebSocketSession storedSession = getSocketSession.execute(user);
        var userAdmin = getSocketAttributesOperations.getGetSocketUserAdmin().execute(storedSession);
        if (Objects.nonNull(userAdmin) && Boolean.TRUE.equals(userAdmin.getAdmin())) {
            var gameId = getSocketAttributesOperations.getGetSocketGameId().execute(storedSession);
            deleteDataOperations.getDeleteRanking().execute();
            deleteDataOperations.getDeleteGame().execute(gameId);
        }

        deleteDataOperations.getDeleteCoordinates().execute(user);
        deleteDataOperations.getDeletePlayUserData().execute(user);
        deleteDataOperations.getDeleteSocketSession().execute(user);

        log.info("Connection closed for user {}", user.getUserName());
    }
}
