package com.example.restaurant.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity class for order products.
 *
 * @author Zhadan Artem
 * @see Serializable
 */

public class OrderProducts implements Serializable {
    private static final long serialVersionUID = 2651066671775436718L;

    private Long orderId;
    private Long productId;

    /**
     * Constructs an <b>OrderProducts</b> without parameters.
     */
    private OrderProducts() {

    }

    /**
     * Constructs an <b>OrderProducts</b> with parameters.
     *
     * @param orderId   order's identifier
     * @param productId product's identifier
     * @see Order
     * @see Product
     */
    private OrderProducts(Long orderId, Long productId) {
        this.orderId = orderId;
        this.productId = productId;
    }

    /**
     * Fabric method for creating new <b>OrderProducts</b>.
     *
     * @return {@link OrderProducts}
     */
    public static OrderProducts createOrderProducts() {
        return new OrderProducts();
    }

    /**
     * Fabric method for creating new <b>OrderProducts</b>.
     *
     * @param orderId   order's identifier
     * @param productId product's identifier
     * @return {@link OrderProducts}
     * @see Order
     * @see Product
     */
    public static OrderProducts createOrderProducts(Long orderId, Long productId) {
        return new OrderProducts(orderId, productId);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderProducts that = (OrderProducts) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, productId);
    }

    @Override
    public String toString() {
        return "OrderProducts { " +
                "orderId = " + orderId +
                ", productId = " + productId +
                '}';
    }
}
