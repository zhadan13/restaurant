package com.example.restaurant.db.dao.impl;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.constants.Payment;
import com.example.restaurant.constants.SQLQuery;
import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.db.dao.OrderDAO;
import com.example.restaurant.db.dao.OrderProductsDAO;
import com.example.restaurant.db.dao.ProductDAO;
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

public class OrderDAOImpl implements OrderDAO {
    private static final Logger LOGGER = LogManager.getLogger(OrderDAOImpl.class);

    private static OrderDAOImpl INSTANCE;

    private OrderDAOImpl() {

    }

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

    @Override
    public Optional<Order> save(Order order) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.INSERT_NEW_ORDER, PreparedStatement.RETURN_GENERATED_KEYS)) {
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

    @Override
    public boolean delete(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.DELETE_ORDER_BY_ID)) {
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

    @Override
    public boolean update(Order order) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.UPDATE_ORDER_BY_ID)) {
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

    @Override
    public Optional<Order> get(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        Order order = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_ORDER_BY_ID)) {
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

    @Override
    public List<Order> getAll() {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_ALL_ORDERS)) {
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

    @Override
    public boolean updateOrderStatus(Long id, OrderStatus status) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.UPDATE_ORDER_STATUS_BY_ID)) {
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

    @Override
    public List<Order> getAllUserOrders(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_ALL_USER_ORDERS_BY_USER_ID)) {
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

    @Override
    public List<Order> getAllUncompletedOrders(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_UNCOMPLETED_USER_ORDERS_BY_USER_ID)) {
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

    @Override
    public Map<Product, Integer> getAllProductsByOrderId(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<Long> productsId = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_ALL_PRODUCTS_ID_BY_ORDER_ID)) {
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

    private Order createOrder(final ResultSet resultSet) throws SQLException {
        return Order.createOrder(resultSet.getLong("id"), resultSet.getLong("user_id"),
                OrderStatus.parseStatus(resultSet.getString("status")), resultSet.getString("address"),
                Payment.parsePayment(resultSet.getString("payment")), resultSet.getDouble("cost"),
                resultSet.getTimestamp("date").toLocalDateTime(), getAllProductsByOrderId(resultSet.getLong("id")));
    }
}
