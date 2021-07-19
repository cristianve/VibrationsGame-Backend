package com.vibration.backend.domain.services.user;

import com.vibration.backend.domain.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateUsernameTest {

    @Test
    void getUsername() {
        CreateUsername createUsername = new CreateUsername();
        User user = createUsername.getUsername();

        assertNotNull(user);
        assertNotNull(user.getUserName());
    }
}