package com.example.restaurant.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordSaltTest {
    @Test
    void testEquals() {
        PasswordSalt passwordSalt = PasswordSalt.createSalt("12345", 1L);
        PasswordSalt passwordSalt1 = PasswordSalt.createSalt("12345", 1L);
        assertEquals(passwordSalt, passwordSalt1);
    }

    @Test
    void testNotEquals() {
        PasswordSalt passwordSalt = PasswordSalt.createSalt("12345", 1L);
        PasswordSalt passwordSalt1 = PasswordSalt.createSalt("123456", 1L);
        assertNotEquals(passwordSalt, passwordSalt1);
    }

    @Test
    void testHashCodeEquals() {
        PasswordSalt passwordSalt = PasswordSalt.createSalt(1L, "12345", 1L);
        PasswordSalt passwordSalt1 = PasswordSalt.createSalt(1L, "12345", 1L);
        assertEquals(passwordSalt.hashCode(), passwordSalt1.hashCode());
    }

    @Test
    void testHashCodeNotEquals() {
        PasswordSalt passwordSalt = PasswordSalt.createSalt(1L, "12345", 1L);
        PasswordSalt passwordSalt1 = PasswordSalt.createSalt(1L, "123456", 1L);
        assertNotEquals(passwordSalt.hashCode(), passwordSalt1.hashCode());
    }
}
