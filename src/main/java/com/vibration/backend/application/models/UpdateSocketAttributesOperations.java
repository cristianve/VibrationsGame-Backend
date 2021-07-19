package com.vibration.backend.application.models;

import com.vibration.backend.domain.services.socket.*;
import org.springframework.stereotype.Component;

@Component
public class UpdateSocketAttributesOperations {

    private final UpdateSocketState updateSocketState;
    private final UpdateSocketAction updateSocketAction;
    private final UpdateSocketGameId updateSocketGameId;
    private final UpdateSocketUser updateSocketUser;
    private final UpdateSocketUserAdmin updateSocketUserAdmin;

    public UpdateSocketAttributesOperations(UpdateSocketState updateSocketState,
                                            UpdateSocketAction updateSocketAction,
                                            UpdateSocketGameId updateSocketGameId,
                                            UpdateSocketUser updateSocketUser,
                                            UpdateSocketUserAdmin updateSocketUserAdmin) {
        this.updateSocketState = updateSocketState;
        this.updateSocketAction = updateSocketAction;
        this.updateSocketGameId = updateSocketGameId;
        this.updateSocketUser = updateSocketUser;
        this.updateSocketUserAdmin = updateSocketUserAdmin;
    }

    public UpdateSocketState getUpdateSocketState() {
        return updateSocketState;
    }

    public UpdateSocketAction getUpdateSocketAction() {
        return updateSocketAction;
    }

    public UpdateSocketGameId getUpdateSocketGameId() {
        return updateSocketGameId;
    }

    public UpdateSocketUser getUpdateSocketUser() {
        return updateSocketUser;
    }

    public UpdateSocketUserAdmin getUpdateSocketUserAdmin() {
        return updateSocketUserAdmin;
    }
}
