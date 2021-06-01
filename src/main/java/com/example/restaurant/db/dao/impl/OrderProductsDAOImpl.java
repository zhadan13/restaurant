package com.example.restaurant.db.dao.impl;

import com.example.restaurant.constants.SQLQuery;
import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.db.dao.OrderProductsDAO;
import com.example.restaurant.model.OrderProducts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderProductsDAOImpl implements OrderProductsDAO {
    private static final Logger LOGGER = LogManager.getLogger(OrderProductsDAOImpl.class);

    private static OrderProductsDAOImpl INSTANCE;

    private OrderProductsDAOImpl() {

    }

    public static OrderProductsDAOImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (OrderProductsDAOImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OrderProductsDAOImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Optional<OrderProducts> save(OrderProducts orderProducts) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.INSERT_NEW_ORDERS_PRODUCTS_BY_ID)) {
            preparedStatement.setLong(1, orderProducts.getOrderId());
            preparedStatement.setLong(2, orderProducts.getProductId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't save order-products", e);
            return Optional.empty();
        } finally {
            POOL.releaseConnection(connection);
        }
        return Optional.of(orderProducts);
    }

    @Override
    public boolean delete(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.DELETE_ALL_PRODUCTS_BY_ORDER_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't delete order-products", e);
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    @Override
    public boolean update(OrderProducts orderProducts) {
        return false;
    }

    @Override
    public Optional<OrderProducts> get(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        OrderProducts orderProducts = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_ALL_ORDERS_PRODUCTS_BY_ORDER_ID)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                orderProducts = createOrderProducts(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get order-products", e);
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.ofNullable(orderProducts);
    }

    @Override
    public List<OrderProducts> getAll() {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<OrderProducts> productsId = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_ALL_ORDERS_PRODUCTS)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                OrderProducts orderProducts = createOrderProducts(resultSet);
                productsId.add(orderProducts);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get all order-products", e);
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return productsId;
    }

    @Override
    public Optional<OrderProducts> save(OrderProducts orderProducts, ConnectionImpl connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.INSERT_NEW_ORDERS_PRODUCTS_BY_ID)) {
            preparedStatement.setLong(1, orderProducts.getOrderId());
            preparedStatement.setLong(2, orderProducts.getProductId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't save order-products", e);
            return Optional.empty();
        } finally {
            POOL.releaseConnection(connection);
        }
        return Optional.of(orderProducts);
    }

    @Override
    public boolean deleteById(OrderProducts orderProducts) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.DELETE_ALL_PRODUCTS_BY_ORDER_AND_PRODUCT_ID)) {
            preparedStatement.setLong(1, orderProducts.getOrderId());
            preparedStatement.setLong(2, orderProducts.getProductId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't delete order-products", e);
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    private OrderProducts createOrderProducts(final ResultSet resultSet) throws SQLException {
        return OrderProducts.createOrderProducts(resultSet.getLong("order_id"),
                resultSet.getLong("product_id"));
    }
}
