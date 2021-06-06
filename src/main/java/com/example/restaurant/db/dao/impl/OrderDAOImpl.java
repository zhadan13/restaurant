package com.example.restaurant.db.dao.impl;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.constants.Payment;
import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.db.dao.*;
import com.example.restaurant.model.Order;
import com.example.restaurant.model.OrderProducts;
import com.example.restaurant.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import static com.example.restaurant.constants.SQLQuery.*;

/**
 * Order Data Access Object implementation.
 *
 * @author Zhadan Artem
 * @see OrderDAO
 * @see DAO
 * @see Order
 */

public class OrderDAOImpl implements OrderDAO {
    private static final Logger LOGGER = LogManager.getLogger(OrderDAOImpl.class);

    /**
     * Singleton instance.
     */
    private static OrderDAOImpl INSTANCE;

    /**
     * Constructs an <b>OrderDAOImpl</b>.
     */
    private OrderDAOImpl() {

    }

    /**
     * Returns already created instance of <b>OrderDAOImpl</b>, or creates new and then returns.
     *
     * @return {@link OrderDAOImpl} instance
     */
    public static OrderDAOImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (OrderDAOImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OrderDAOImpl();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Order> save(Order order) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_ORDER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setString(2, order.getStatus().name());
            preparedStatement.setString(3, order.getAddress());
            preparedStatement.setString(4, order.getPayment().name());
            preparedStatement.setDouble(5, order.getCost());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(order.getDate()));
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                order.setId(id);

                // Saving products for order in current transaction
                OrderProductsDAO orderProductsDAO = OrderProductsDAOImpl.getInstance();
                for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
                    Product product = entry.getKey();
                    for (int i = 0; i < entry.getValue(); i++) {
                        OrderProducts orderProducts = OrderProducts.createOrderProducts(order.getId(), product.getId());
                        Optional<OrderProducts> optionalOrderProducts = orderProductsDAO.save(orderProducts, connection);
                        optionalOrderProducts.orElseThrow(() -> new SQLException("Can't save order products in transaction for save order"));
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOGGER.error("Can't rollback", ex);
            }
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                LOGGER.error("Can't set autocommit for connection to TRUE");
            }
            LOGGER.error("Can't save order", e);
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.of(order);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_BY_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't delete order", e);
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Order order) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_BY_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, order.getStatus().name());
            preparedStatement.setString(2, order.getAddress());
            preparedStatement.setDouble(3, order.getCost());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(order.getDate()));
            preparedStatement.setLong(5, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't update order", e);
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Order> get(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        Order order = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER_BY_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = createOrder(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get order", e);
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.ofNullable(order);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getAll() {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS)) {
            connection.setAutoCommit(true);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = createOrder(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get all orders", e);
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return orders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateOrder(Long id, Map<Product, Integer> products) {
        OrderProductsDAO orderProductsDAO = OrderProductsDAOImpl.getInstance();
        boolean result = orderProductsDAO.delete(id);
        if (result) {
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product product = entry.getKey();
                OrderProducts orderProducts = OrderProducts.createOrderProducts(id, product.getId());
                Optional<OrderProducts> optionalOrderProducts = orderProductsDAO.save(orderProducts);
                if (!optionalOrderProducts.isPresent()) {
                    LOGGER.info("Can't update order");
                    return false;
                }
            }
            return true;
        }
        LOGGER.info("Can't update order");
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateOrderStatus(Long id, OrderStatus status) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS_BY_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, status.name());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't update order status", e);
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getAllUserOrders(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USER_ORDERS_BY_USER_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = createOrder(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get all user orders", e);
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return orders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Order> getAllUncompletedOrders(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_UNCOMPLETED_USER_ORDERS_BY_USER_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order = createOrder(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get all user uncompleted orders", e);
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return orders;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Product, Integer> getAllProductsByOrderId(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<Long> productsId = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCTS_ID_BY_ORDER_ID)) {
            connection.setAutoCommit(true);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                productsId.add(productId);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get all products from order by order id", e);
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        Map<Product, Integer> products = new HashMap<>();
        if (productsId.size() > 0) {
            ProductDAO productDAO = ProductDAOImpl.getInstance();
            for (Long productId : productsId) {
                Optional<Product> optionalProduct = productDAO.get(productId);
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    Integer value = products.get(product);
                    if (value == null || value == 0) {
                        value = 1;
                    } else {
                        value++;
                    }
                    products.put(product, value);
                } else {
                    LOGGER.warn("Can't find product");
                }
            }
        }
        return products;
    }

    /**
     * Method mapping <b>ResultSet</b> to <b>Order</b>.
     *
     * @param resultSet resultSet which should be mapped
     * @return {@link Order}
     * @throws SQLException {@inheritDoc}
     */
    private Order createOrder(final ResultSet resultSet) throws SQLException {
        return Order.createOrder(resultSet.getLong("id"), resultSet.getLong("user_id"),
                OrderStatus.parseStatus(resultSet.getString("status")), resultSet.getString("address"),
                Payment.parsePayment(resultSet.getString("payment")), resultSet.getDouble("cost"),
                resultSet.getTimestamp("date").toLocalDateTime(), getAllProductsByOrderId(resultSet.getLong("id")));
    }
}
