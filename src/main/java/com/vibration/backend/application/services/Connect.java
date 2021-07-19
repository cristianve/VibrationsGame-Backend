package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.application.models.UpdateSocketAttributesOperations;
import com.vibration.backend.domain.model.SocketAction;
import com.vibration.backend.domain.model.SocketState;
import com.vibration.backend.domain.model.User;
import com.vibration.backend.domain.services.session.SaveSocketSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class Connect {

    private static final Logger log = LogManager.getLogger(Connect.class);

    private final SaveSocketSession saveSocketSession;
    private final UpdateSocketAttributesOperations updateSocketAttributesOperations;
    private final GetSocketAttributesOperations getSocketAttributesOperations;

    public Connect(SaveSocketSession saveSocketSession,
                   UpdateSocketAttributesOperations updateSocketAttributesOperations,
                   GetSocketAttributesOperations getSocketAttributesOperations) {
        this.saveSocketSession = saveSocketSession;
        this.updateSocketAttributesOperations = updateSocketAttributesOperations;
        this.getSocketAttributesOperations = getSocketAttributesOperations;
    }

    public User execute(WebSocketSession session) {
        //Assign username to socket session
        var user = getSocketAttributesOperations.getGetSocketUser().execute(session);
        log.info("User obtained from session is {}", user.getUserName());

        updateSocketAttributesOperations.getUpdateSocketUser().execute(session, user);

        //Assign socket state to CONNECTED
        updateSocketAttributesOperations.getUpdateSocketState().execute(session, SocketState.CONNECTED);
        updateSocketAttributesOperations.getUpdateSocketAction().execute(session, SocketAction.IDLE);

        //Store in map username session
        saveSocketSession.execute(user, session);

        log.info("Connection established for user {} with status {}", user.getUserName(),
                SocketState.CONNECTED);

        return user;
    }

}
