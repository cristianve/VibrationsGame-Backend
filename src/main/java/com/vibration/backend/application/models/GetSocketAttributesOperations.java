package com.vibration.backend.application.models;

import com.vibration.backend.domain.services.socket.*;
import org.springframework.stereotype.Component;

@Component
public class GetSocketAttributesOperations {

    private final GetSocketState getSocketState;
    private final GetSocketAction getSocketAction;
    private final GetSocketGameId getSocketGameId;
    private final GetSocketUser getSocketUser;
    private final GetSocketUserAdmin getSocketUserAdmin;

    public GetSocketAttributesOperations(GetSocketState getSocketState,
                                         GetSocketAction getSocketAction,
                                         GetSocketGameId getSocketGameId,
                                         GetSocketUser getSocketUser,
                                         GetSocketUserAdmin getSocketUserAdmin) {
        this.getSocketState = getSocketState;
        this.getSocketAction = getSocketAction;
        this.getSocketGameId = getSocketGameId;
        this.getSocketUser = getSocketUser;
        this.getSocketUserAdmin = getSocketUserAdmin;
    }

    public GetSocketState getGetSocketState() {
        return getSocketState;
    }

    public GetSocketAction getGetSocketAction() {
        return getSocketAction;
    }

    public GetSocketGameId getGetSocketGameId() {
        return getSocketGameId;
    }

    public GetSocketUser getGetSocketUser() {
        return getSocketUser;
    }

    public GetSocketUserAdmin getGetSocketUserAdmin() {
        return getSocketUserAdmin;
    }
}
