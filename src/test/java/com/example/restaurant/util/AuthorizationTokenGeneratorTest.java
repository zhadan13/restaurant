package com.example.restaurant.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationTokenGeneratorTest {
    @Test
    void generateTokenCorrectLength() {
        int length = 20;
        assertEquals(AuthorizationTokenGenerator.generateToken(length).length(), length);
    }

    @Test
    void generateTokenUnique() {
        int length = 20;
        assertNotEquals(AuthorizationTokenGenerator.generateToken(length), AuthorizationTokenGenerator.generateToken(length));
    }
}
