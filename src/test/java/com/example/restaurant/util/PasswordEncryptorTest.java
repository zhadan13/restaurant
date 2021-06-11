package com.example.restaurant.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordEncryptorTest {
    @Test
    void generateSecurePassword() {
        // given
        int saltLength = 20;
        String password = "123456";
        String salt = PasswordEncryptor.getSalt(saltLength);
        String salt1 = PasswordEncryptor.getSalt(saltLength);

        // then
        assertNotEquals(PasswordEncryptor.generateSecurePassword(password.toCharArray(), salt), PasswordEncryptor.generateSecurePassword(password.toCharArray(), salt1));
    }

    @Test
    void verifyUserPassword() {
        // given
        int saltLength = 20;
        String password = "123456";
        String salt = PasswordEncryptor.getSalt(saltLength);

        // when
        String encryptedPassword = PasswordEncryptor.generateSecurePassword(password.toCharArray(), salt);
        boolean isPasswordsEquals = PasswordEncryptor.verifyUserPassword(password.toCharArray(), encryptedPassword.toCharArray(), salt);

        // then
        assertTrue(isPasswordsEquals);
    }
}
