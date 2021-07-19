package com.vibration.backend.domain.model;

import java.util.Objects;

public class UserAdmin {

    private final Boolean admin;

    public UserAdmin(Boolean admin) {
        this.admin = Objects.requireNonNullElse(admin, Boolean.FALSE);
    }

    public Boolean getAdmin() {
        return admin;
    }
}
