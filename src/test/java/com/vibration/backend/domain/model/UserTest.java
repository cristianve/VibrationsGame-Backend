package com.vibration.backend.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void getUserName() {
        User user = new User("username");

        assertNotNull(user);
        assertEquals("username", user.getUserName());
    }
}