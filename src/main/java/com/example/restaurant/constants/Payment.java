package com.example.restaurant.constants;

public enum Payment {
    CASH, CARD_ONLINE, CARD_OFFLINE;

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
