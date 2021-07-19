package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.SessionException;
import com.vibration.backend.domain.services.session.GetSocketSession;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class CheckSocketUserAdmin {

    private final GetSocketAttributesOperations getSocketAttributesOperations;
    private final GetSocketSession getSocketSession;

    public CheckSocketUserAdmin(GetSocketAttributesOperations getSocketAttributesOperations,
                                GetSocketSession getSocketSession) {
        this.getSocketAttributesOperations = getSocketAttributesOperations;
        this.getSocketSession = getSocketSession;
    }

    public void execute(WebSocketSession session) throws SessionException {
        var userAdmin = getSocketAttributesOperations.getGetSocketUserAdmin().execute(session);

        if (!Boolean.TRUE.equals(userAdmin.getAdmin())) {
            throw new SessionException("User needs to be admin to perform this operation");
        }

        var user = getSocketAttributesOperations.getGetSocketUser().execute(session);
        var sessionStored = getSocketSession.execute(user);

        var getSocketUserAdmin = getSocketAttributesOperations.getGetSocketUserAdmin();
        if (!Boolean.TRUE.equals(getSocketUserAdmin.execute(sessionStored).getAdmin())) {
            throw new SessionException("User needs to be admin to perform this operation");
        }
    }
}
