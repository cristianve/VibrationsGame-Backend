package com.vibration.backend.domain.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserAdminTest {

    private static Stream<Arguments> provideBooleanToCreate() {
        return Stream.of(
                Arguments.of(null, Boolean.FALSE),
                Arguments.of(Boolean.FALSE, Boolean.FALSE),
                Arguments.of(Boolean.TRUE, Boolean.TRUE)
        );
    }

    @ParameterizedTest
    @MethodSource("provideBooleanToCreate")
    void getAdmin(Boolean isAdmin, Boolean expected) {
        UserAdmin userAdmin = new UserAdmin(isAdmin);

        assertEquals(expected, userAdmin.getAdmin());
    }
}