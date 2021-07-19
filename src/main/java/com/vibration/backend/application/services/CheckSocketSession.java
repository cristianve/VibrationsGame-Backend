package com.vibration.backend.application.services;

import com.vibration.backend.application.models.GetSocketAttributesOperations;
import com.vibration.backend.domain.exceptions.SessionException;
import com.vibration.backend.domain.services.session.GetSocketSession;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;

@Service
public class CheckSocketSession {

    private final GetSocketAttributesOperations getSocketAttributesOperations;
    private final GetSocketSession getSocketSession;

    public CheckSocketSession(GetSocketAttributesOperations getSocketAttributesOperations, GetSocketSession getSocketSession) {
        this.getSocketAttributesOperations = getSocketAttributesOperations;
        this.getSocketSession = getSocketSession;
    }

    public WebSocketSession execute(WebSocketSession session) throws SessionException {
        if (Objects.isNull(session)) {
            throw new SessionException("Received session can't be null");
        }

        var user = getSocketAttributesOperations.getGetSocketUser().execute(session);
        var sessionStored = getSocketSession.execute(user);

        if (Objects.isNull(sessionStored)) {
            throw new SessionException("Stored session not found");
        }

        var getSocketState = getSocketAttributesOperations.getGetSocketState();
        var sessionState = getSocketState.execute(sessionStored);

        if (Objects.isNull(sessionState)) {
            throw new SessionException("Socket state can't be null");
        }

        return sessionStored;
    }
}
