package com.example.restaurant.constants;

import com.example.restaurant.model.Order;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public static List<Order> filter(List<Order> orders, String filter, String sorting) {
        if (!filter.equalsIgnoreCase("DEFAULT")) {
            if (filter.equalsIgnoreCase(OrderStatus.ACCEPTED.name())) {
                orders = orders
                        .stream()
                        .filter(order -> order.getStatus() == OrderStatus.ACCEPTED)
                        .collect(Collectors.toList());
            } else if (filter.equalsIgnoreCase(OrderStatus.CONFIRMED.name())) {
                orders = orders
                        .stream()
                        .filter(order -> order.getStatus() == OrderStatus.CONFIRMED)
                        .collect(Collectors.toList());
            } else if (filter.equalsIgnoreCase(OrderStatus.PREPARING.name())) {
                orders = orders.stream()
                        .filter(order -> order.getStatus() == OrderStatus.PREPARING)
                        .collect(Collectors.toList());
            } else if (filter.equalsIgnoreCase(OrderStatus.DELIVERING.name())) {
                orders = orders.stream()
                        .filter(order -> order.getStatus() == OrderStatus.DELIVERING)
                        .collect(Collectors.toList());
            } else if (filter.equalsIgnoreCase(OrderStatus.COMPLETED.name())) {
                orders = orders.stream()
                        .filter(order -> order.getStatus() == OrderStatus.COMPLETED)
                        .collect(Collectors.toList());
            } else if (filter.equalsIgnoreCase(OrderStatus.REJECTED.name())) {
                orders = orders.stream()
                        .filter(order -> order.getStatus() == OrderStatus.REJECTED)
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
            } else if (sorting.equalsIgnoreCase("DATE FROM OLD TO NEW")) {
                orders.sort(Comparator.comparing(Order::getDate));
            } else if (sorting.equalsIgnoreCase("DATE FROM NEW TO OLD")) {
                orders.sort(Comparator.comparing(Order::getDate).reversed());
            }
        }
        return orders;
    }
}
