package com.example.restaurant.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Util class for encrypting password.
 *
 * @author Zhadan Artem
 */

public final class PasswordEncryptor {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 512;

    private PasswordEncryptor() {

    }

    /**
     * Returns generated salt for password.
     *
     * @param length length of salt
     * @return {@link String} salt
     */
    public static String getSalt(final int length) {
        StringBuilder salt = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            salt.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return String.valueOf(salt);
    }

    /**
     * Returns encrypted password as byte array.
     *
     * @param password password, which has to be hashed
     * @param salt     salt for current password
     * @return byte array representation of encrypted password
     */
    private static byte[] hash(char[] password, final byte[] salt) {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            return secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            pbeKeySpec.clearPassword();
        }
    }

    /**
     * Returns encrypted password.
     *
     * @param password password, which has to be hashed
     * @param salt     salt for current password
     * @return {@link String} encrypted password
     */
    public static String generateSecurePassword(final char[] password, final String salt) {
        byte[] securePassword = hash(password, salt.getBytes());
        return Base64.getEncoder().encodeToString(securePassword);
    }

    /**
     * Method verifies user password.
     * Returns <tt>true</tt> if provided and correct password equals, otherwise returns <tt>false</tt>.
     *
     * @param providedPassword provided password for verification
     * @param securedPassword  correct secured password
     * @param salt             salt for encryption current password
     * @return <tt>true</tt> if provided and correct password equals, otherwise returns <tt>false</tt>
     */
    public static boolean verifyUserPassword(final char[] providedPassword, final char[] securedPassword, final String salt) {
        String newSecurePassword = generateSecurePassword(providedPassword, salt);
        return newSecurePassword.equals(String.valueOf(securedPassword));
    }
}
