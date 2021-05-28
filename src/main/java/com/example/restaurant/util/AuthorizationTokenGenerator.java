package com.example.restaurant.util;

import java.security.SecureRandom;
import java.util.Random;

public final class AuthorizationTokenGenerator {
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private AuthorizationTokenGenerator() {

    }

    public static String generateToken(final int length) {
        StringBuilder token = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            token.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return String.valueOf(token);
    }
}
