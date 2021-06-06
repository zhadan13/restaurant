package com.example.restaurant.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for payment validation.
 *
 * @author Zhadan Artem
 * @see com.example.restaurant.constants.Payment
 */

public final class PaymentValidator {
    private static final int CARD_NUMBER_LENGTH = 16;
    private static final int MIN_NAME_LENGTH = 1;
    private static final int MIN_EXPIRATION_DATE_LENGTH = 4;
    private static final int CVV_CODE_LENGTH = 3;

    private PaymentValidator() {

    }

    /**
     * Method validates payment data.
     * Returns <tt>true</tt> if payment data valid, otherwise returns <tt>false</tt>.
     *
     * @param cardNumber card number
     * @param name       card holder's name
     * @param date       expiration date
     * @param cvv        CVV code
     * @return <tt>true</tt> if payment data valid, otherwise returns <tt>false</tt>
     * @see com.example.restaurant.constants.Payment
     */
    public static boolean validatePayment(final String cardNumber, final String name, final String date, final String cvv) {
        return validateCardNumber(cardNumber) && validateName(name) && validateExpirationDate(date) && validateCVV(cvv);
    }

    /**
     * Method validates card number.
     * Returns <tt>true</tt> if card number valid, otherwise returns <tt>false</tt>.
     *
     * @param cardNumber card number
     * @return <tt>true</tt> if card number valid, otherwise returns <tt>false</tt>
     */
    public static boolean validateCardNumber(final String cardNumber) {
        if (cardNumber != null && cardNumber.trim().length() == CARD_NUMBER_LENGTH) {
            String regex = "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(cardNumber);
            return matcher.matches();
        }
        return false;
    }

    /**
     * Method validates card holder's name.
     * Returns <tt>true</tt> if card holder's name valid, otherwise returns <tt>false</tt>.
     *
     * @param name card holder's name
     * @return <tt>true</tt> if card holder's name valid, otherwise returns <tt>false</tt>
     */
    public static boolean validateName(final String name) {
        return name != null && name.trim().length() >= MIN_NAME_LENGTH;
    }

    /**
     * Method validates card expiration date.
     * Returns <tt>true</tt> if card expiration date valid, otherwise returns <tt>false</tt>.
     *
     * @param date card expiration date
     * @return <tt>true</tt> if card expiration date valid, otherwise returns <tt>false</tt>
     */
    public static boolean validateExpirationDate(final String date) {
        if (date != null && date.trim().length() >= MIN_EXPIRATION_DATE_LENGTH) {
            String regex = "^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(date);
            return matcher.matches();
        }
        return false;
    }

    /**
     * Method validates card CVV code.
     * Returns <tt>true</tt> if card CVV code valid, otherwise returns <tt>false</tt>.
     *
     * @param cvv card CVV code
     * @return <tt>true</tt> if card CVV code valid, otherwise returns <tt>false</tt>
     */
    public static boolean validateCVV(final String cvv) {
        if (cvv != null && cvv.trim().length() == CVV_CODE_LENGTH) {
            String regex = "^[0-9]{3}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(cvv);
            return matcher.matches();
        }
        return false;
    }
}
