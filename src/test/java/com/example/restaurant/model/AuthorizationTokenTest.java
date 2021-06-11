package com.example.restaurant.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationTokenTest {
    @Test
    void testEquals() {
        AuthorizationToken authorizationToken = AuthorizationToken.createToken("12345", 1L);
        AuthorizationToken authorizationToken1 = AuthorizationToken.createToken("12345", 1L);
        assertEquals(authorizationToken, authorizationToken1);
    }

    @Test
    void testNotEquals() {
        AuthorizationToken authorizationToken = AuthorizationToken.createToken("12345", 1L);
        AuthorizationToken authorizationToken1 = AuthorizationToken.createToken("12345", 2L);
        assertNotEquals(authorizationToken, authorizationToken1);
    }

    @Test
    void testHashCodeEquals() {
        AuthorizationToken authorizationToken = AuthorizationToken.createToken(1L, 1L, "12345");
        AuthorizationToken authorizationToken1 = AuthorizationToken.createToken(1L, 1L, "12345");
        assertEquals(authorizationToken.hashCode(), authorizationToken1.hashCode());
    }

    @Test
    void testHashCodeNotEquals() {
        AuthorizationToken authorizationToken = AuthorizationToken.createToken(1L, 1L, "12345");
        AuthorizationToken authorizationToken1 = AuthorizationToken.createToken(1L, 2L, "12345");
        assertNotEquals(authorizationToken.hashCode(), authorizationToken1.hashCode());
    }
}
