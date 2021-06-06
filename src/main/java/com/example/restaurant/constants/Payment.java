package com.example.restaurant.constants;

/**
 * Enum with possible payment types.
 *
 * @author Zhadan Artem
 */

public enum Payment {
    CASH, CARD_ONLINE, CARD_OFFLINE;

    /**
     * Returns the enum value corresponding to the string representation, or <tt>null</tt> if string representation is incorrect.
     *
     * @param payment the string value of enum value
     * @return {@link Payment} value, or <tt>null</tt> if category string can't parse to enum value
     */
    public static Payment parsePayment(final String payment) {
        if (CASH.name().equalsIgnoreCase(payment)) {
            return CASH;
        }
        if (CARD_ONLINE.name().equalsIgnoreCase(payment)) {
            return CARD_ONLINE;
        }
        if (CARD_OFFLINE.name().equalsIgnoreCase(payment)) {
            return CARD_OFFLINE;
        }
        return null;
    }
}
