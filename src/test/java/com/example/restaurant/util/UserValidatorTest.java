package com.example.restaurant.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {
    @Test
    void validateUser() {
        assertTrue(UserValidator.validateUser("user@mail.com", "123456Aa", "0500123456", "User"));
    }

    @Test
    void validateUserWrong() {
        assertFalse(UserValidator.validateUser("user.mail.com", "123456", "0500123456", "User"));
    }

    @Test
    void validateEmail() {
        assertTrue(UserValidator.validateEmail("user@mail.com"));
    }

    @Test
    void validateEmailWrong() {
        assertFalse(UserValidator.validateEmail("user.mail.com"));
    }

    @Test
    void validatePassword() {
        assertTrue(UserValidator.validatePassword("123456Aa"));
    }

    @Test
    void validatePasswordWrong() {
        assertFalse(UserValidator.validatePassword("abcdef"));
    }

    @Test
    void validatePhoneNumber() {
        assertTrue(UserValidator.validatePhoneNumber("0501234567"));
    }

    @Test
    void validatePhoneNumberWrong() {
        assertFalse(UserValidator.validatePhoneNumber("050000123424243242"));
    }

    @Test
    void validateName() {
        assertTrue(UserValidator.validateName("user"));
    }

    @Test
    void validateNameWrong() {
        assertFalse(UserValidator.validateName("   "));
    }
}
