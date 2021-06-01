package com.example.restaurant.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PaymentValidator {
    private static final int CARD_NUMBER_LENGTH = 16;
    private static final int MIN_NAME_LENGTH = 1;
    private static final int CVV_NUMBER_LENGTH = 3;

    private PaymentValidator() {

    }

    public static boolean validateCardNumber(final String cardNumber) {
        if (cardNumber != null && cardNumber.trim().length() == CARD_NUMBER_LENGTH) {
            String regex = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(cardNumber);
            return matcher.matches();
        }
        return false;
    }

    public static boolean validateName(final String name) {
        return name != null && name.trim().length() >= MIN_NAME_LENGTH;
    }

    public static boolean validateExpirationDate(final String date) {
        if (date != null && date.trim().length() != 0) {
            String regex = "^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(date);
            return matcher.matches();
        }
        return false;
    }

    public static boolean validateCVV(final String cvv) {
        if (cvv != null && cvv.trim().length() == CVV_NUMBER_LENGTH) {
            String regex = "^[0-9]{3}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(cvv);
            return matcher.matches();
        }
        return false;
    }
}
