package com.example.restaurant.db.dao.impl;

import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.db.dao.DAO;
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

import static com.example.restaurant.constants.SQLQuery.*;

/**
 * OrderProducts Data Access Object implementation.
 *
 * @author Zhadan Artem
 * @see OrderProductsDAO
 * @see DAO
 * @see OrderProducts
 */

public class OrderProductsDAOImpl implements OrderProductsDAO {
    private static final Logger LOGGER = LogManager.getLogger(OrderProductsDAOImpl.class);

    /**
     * Singleton instance.
     */
    private static OrderProductsDAOImpl INSTANCE;

    /**
     * Constructs an <b>OrderProductsDAOImpl</b>.
     */
    private OrderProductsDAOImpl() {

    }

    /**
     * Returns already created instance of <b>OrderProductsDAOImpl</b>, or creates new and then returns.
     *
     * @return {@link OrderProductsDAOImpl} instance
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<OrderProducts> save(OrderProducts orderProducts) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_ORDERS_PRODUCTS_BY_ID)) {
            connection.setAutoCommit(true);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_PRODUCTS_BY_ORDER_ID)) {
            connection.setAutoCommit(true);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(OrderProducts orderProducts) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<OrderProducts> get(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        OrderProducts orderProducts = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS_PRODUCTS_BY_ORDER_ID)) {
            connection.setAutoCommit(true);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OrderProducts> getAll() {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<OrderProducts> productsId = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_ORDERS_PRODUCTS)) {
            connection.setAutoCommit(true);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<OrderProducts> save(OrderProducts orderProducts, ConnectionImpl connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_ORDERS_PRODUCTS_BY_ID)) {
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

    /**
     * Method mapping <b>ResultSet</b> to <b>OrderProducts</b>.
     *
     * @param resultSet resultSet which should be mapped
     * @return {@link OrderProducts}
     * @throws SQLException {@inheritDoc}
     */
    private OrderProducts createOrderProducts(final ResultSet resultSet) throws SQLException {
        return OrderProducts.createOrderProducts(resultSet.getLong("order_id"),
                resultSet.getLong("product_id"));
    }
}
