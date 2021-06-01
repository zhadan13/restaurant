package com.example.restaurant.model;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.constants.Payment;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class Order implements Serializable {
    private static final long serialVersionUID = 2520944750127782682L;

    private Long id;
    private Long userId;
    private OrderStatus status;
    private String address;
    private Payment payment;
    private Double cost;
    private LocalDateTime date;
    private Map<Product, Integer> products;

    private Order() {

    }

    private Order(Long userId, OrderStatus status, String address, Payment payment, Double cost, LocalDateTime date, Map<Product, Integer> products) {
        this.userId = userId;
        this.status = status;
        this.address = address;
        this.payment = payment;
        this.cost = cost;
        this.date = date;
        this.products = products;
    }

    private Order(Long id, Long userId, OrderStatus status, String address, Payment payment, Double cost, LocalDateTime date, Map<Product, Integer> products) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.address = address;
        this.payment = payment;
        this.cost = cost;
        this.date = date;
        this.products = products;
    }

    public static Order createOrder() {
        return new Order();
    }

    public static Order createOrder(Long userId, OrderStatus status, String address, Payment payment, Double cost, LocalDateTime date, Map<Product, Integer> products) {
        return new Order(userId, status, address, payment, cost, date, products);
    }

    public static Order createOrder(Long id, Long userId, OrderStatus status, String address, Payment payment, Double cost, LocalDateTime date, Map<Product, Integer> products) {
        return new Order(id, userId, status, address, payment, cost, date, products);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(userId, order.userId)
                && status == order.status && Objects.equals(address, order.address)
                && payment == order.payment && Objects.equals(cost, order.cost)
                && Objects.equals(date, order.date) && Objects.equals(products, order.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, status, address, payment, cost, date, products);
    }

    @Override
    public String toString() {
        return "Order { " +
                "id = " + id +
                ", userId = " + userId +
                ", status = " + status +
                ", address = '" + address + '\'' +
                ", payment = " + payment +
                ", cost = " + cost +
                ", date = " + date +
                ", products = " + products +
                '}';
    }
}
