package com.example.restaurant.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Util class for generating authorization token for user.
 *
 * @author Zhadan Artem
 * @see com.example.restaurant.model.AuthorizationToken
 */

public final class AuthorizationTokenGenerator {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private AuthorizationTokenGenerator() {

    }

    /**
     * Returns generated token with fixed length.
     *
     * @param length length of token
     * @return {@link String} token
     */
    public static String generateToken(final int length) {
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return String.valueOf(token);
    }
}
