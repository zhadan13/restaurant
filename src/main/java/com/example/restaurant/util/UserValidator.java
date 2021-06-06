package com.example.restaurant.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for user validation.
 *
 * @author Zhadan Artem
 * @see com.example.restaurant.model.User
 */

public final class UserValidator {
    private static final int MIN_EMAIL_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MIN_PHONE_NUMBER_LENGTH = 10;
    private static final int MIN_NAME_LENGTH = 1;

    private UserValidator() {

    }

    /**
     * Method validates user data.
     * Returns <tt>true</tt> if user data valid, otherwise returns <tt>false</tt>.
     *
     * @param email       user's email
     * @param password    user's password
     * @param phoneNumber user's phone number
     * @param name        user name
     * @return <tt>true</tt> if user data valid, otherwise returns <tt>false</tt>
     * @see com.example.restaurant.model.User
     */
    public static boolean validateUser(final String email, final String password, final String phoneNumber, final String name) {
        return validateEmail(email) && validatePassword(password) && validatePhoneNumber(phoneNumber) && validateName(name);
    }

    /**
     * Method validates user's email.
     * Returns <tt>true</tt> if email valid, otherwise returns <tt>false</tt>.
     *
     * @param email user's email
     * @return <tt>true</tt> if email valid, otherwise returns <tt>false</tt>
     */
    public static boolean validateEmail(final String email) {
        if (email != null && email.trim().length() >= MIN_EMAIL_LENGTH) {
            String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
        return false;
    }

    /**
     * Method validates user's password.
     * Returns <tt>true</tt> if password valid, otherwise returns <tt>false</tt>.
     *
     * @param password user's password
     * @return <tt>true</tt> if password valid, otherwise returns <tt>false</tt>
     */
    public static boolean validatePassword(final String password) {
        if (password != null && password.trim().length() >= MIN_PASSWORD_LENGTH) {
            String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(password);
            return matcher.matches();
        }
        return false;
    }

    /**
     * Method validates user's phone number.
     * Returns <tt>true</tt> if phone number valid, otherwise returns <tt>false</tt>.
     *
     * @param phoneNumber user's phone number
     * @return <tt>true</tt> if phone number valid, otherwise returns <tt>false</tt>
     */
    public static boolean validatePhoneNumber(final String phoneNumber) {
        if (phoneNumber != null && phoneNumber.trim().length() >= MIN_PHONE_NUMBER_LENGTH) {
            String regex = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                    + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                    + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(phoneNumber);
            return matcher.matches();
        }
        return false;
    }

    /**
     * Method validates user name.
     * Returns <tt>true</tt> if name valid, otherwise returns <tt>false</tt>.
     *
     * @param name user name
     * @return <tt>true</tt> if name valid, otherwise returns <tt>false</tt>
     */
    public static boolean validateName(final String name) {
        return name != null && name.trim().length() >= MIN_NAME_LENGTH;
    }
}
