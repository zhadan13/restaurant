package com.example.restaurant.constants;

import com.example.restaurant.model.Order;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum with possible statuses for order.
 *
 * @author Zhadan Artem
 */

public enum OrderStatus {
    ACCEPTED, CONFIRMED, PREPARING, DELIVERING, COMPLETED, REJECTED;

    /**
     * Returns the enum value corresponding to the string representation, or <tt>null</tt> if string representation is incorrect.
     *
     * @param status the string value of enum value
     * @return {@link OrderStatus} value, or <tt>null</tt> if category string can't parse to enum value
     */
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

    /**
     * Returns list filtered by category and sorted products.
     *
     * @param orders  the list of {@link Order} to be filtered and sorted
     * @param filter  the string category by which the list will be filtered
     * @param sorting the string parameter by which the list will be sorted
     * @return filtered list
     * @see Order
     */
    public static List<Order> filter(List<Order> orders, final String filter, final String sorting) {
        if (!filter.equalsIgnoreCase("DEFAULT")) {
            if (filter.equalsIgnoreCase(ACCEPTED.name())) {
                orders = orders
                        .stream()
                        .filter(order -> order.getStatus() == ACCEPTED)
                        .collect(Collectors.toList());
            } else if (filter.equalsIgnoreCase(CONFIRMED.name())) {
                orders = orders
                        .stream()
                        .filter(order -> order.getStatus() == CONFIRMED)
                        .collect(Collectors.toList());
            } else if (filter.equalsIgnoreCase(PREPARING.name())) {
                orders = orders.stream()
                        .filter(order -> order.getStatus() == PREPARING)
                        .collect(Collectors.toList());
            } else if (filter.equalsIgnoreCase(DELIVERING.name())) {
                orders = orders.stream()
                        .filter(order -> order.getStatus() == DELIVERING)
                        .collect(Collectors.toList());
            } else if (filter.equalsIgnoreCase(COMPLETED.name())) {
                orders = orders.stream()
                        .filter(order -> order.getStatus() == COMPLETED)
                        .collect(Collectors.toList());
            } else if (filter.equalsIgnoreCase(REJECTED.name())) {
                orders = orders.stream()
                        .filter(order -> order.getStatus() == REJECTED)
                        .collect(Collectors.toList());
            } else if (filter.equalsIgnoreCase("UNCOMPLETED")) {
                orders = orders.stream()
                        .filter(order -> order.getStatus().ordinal() != 4 && order.getStatus().ordinal() != 5)
                        .collect(Collectors.toList());
            }
        }
        if (!sorting.equalsIgnoreCase("DEFAULT")) {
            if (sorting.equalsIgnoreCase("USER ID")) {
                orders.sort(Comparator.comparing(Order::getUserId));
            } else if (sorting.equalsIgnoreCase("STATUS")) {
                orders.sort(Comparator.comparing(Order::getStatus));
            } else if (sorting.equalsIgnoreCase("DATE OLD TO NEW")) {
                orders.sort(Comparator.comparing(Order::getDate));
            } else if (sorting.equalsIgnoreCase("DATE NEW TO OLD")) {
                orders.sort(Comparator.comparing(Order::getDate).reversed());
            }
        }
        return orders;
    }
}
