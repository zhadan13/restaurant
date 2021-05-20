package com.example.restaurant.constants;

public enum OrderStatus {
    ACCEPTED, CONFIRMED, PREPARING, DELIVERING, COMPLETED, REJECTED;

    public static OrderStatus parseStatus(final String status) {
        if (ACCEPTED.name().equalsIgnoreCase(status)) {
            return ACCEPTED;
        }
        if (CONFIRMED.name().equalsIgnoreCase(status)) {
            return CONFIRMED;
        }
        if (PREPARING.name().equalsIgnoreCase(status)) {
            return PREPARING;
        }
        if (DELIVERING.name().equalsIgnoreCase(status)) {
            return DELIVERING;
        }
        if (COMPLETED.name().equalsIgnoreCase(status)) {
            return COMPLETED;
        }
        if (REJECTED.name().equalsIgnoreCase(status)) {
            return REJECTED;
        }
        return null;
    }
}
