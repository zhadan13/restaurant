package com.example.restaurant.db.dao.impl;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.constants.Payment;
import com.example.restaurant.constants.SQLQuery;
import com.example.restaurant.db.connection_pool.ConnectionPool;
import com.example.restaurant.db.connection_pool.Pool;
import com.example.restaurant.db.dao.OrderDAO;
import com.example.restaurant.db.dao.OrderProductsDAO;
import com.example.restaurant.db.dao.ProductDAO;
import com.example.restaurant.model.Order;
import com.example.restaurant.model.OrderProducts;
import com.example.restaurant.model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class OrderDAOImpl implements OrderDAO {
    private static OrderDAOImpl INSTANCE;
    private static final Pool POOL = Pool.getInstance();

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
        ConnectionPool connection = POOL.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.INSERT_NEW_ORDER, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, order.getUserId());
            preparedStatement.setString(2, order.getStatus().name());
            preparedStatement.setString(3, order.getAddress());
            preparedStatement.setString(4, order.getPayment().name());
            preparedStatement.setDouble(5, order.getCost());
            preparedStatement.setTimestamp(6, order.getDate());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long field = resultSet.getLong(1);
                order.setId(field);
                OrderProductsDAO orderProductsDAO = OrderProductsDAOImpl.getInstance();
                for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
                    Product product = entry.getKey();
                    for (int i = 0; i < entry.getValue(); i++) {
                        OrderProducts orderProducts = OrderProducts.createOrderProducts(order.getId(), product.getId());
                        orderProductsDAO.save(orderProducts);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.of(order);
    }

    @Override
    public boolean delete(Long id) {
        ConnectionPool connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.DELETE_ORDER_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    @Override
    public boolean update(Order order) {
        ConnectionPool connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.UPDATE_ORDER_BY_ID)) {
            preparedStatement.setString(1, order.getStatus().name());
            preparedStatement.setString(2, order.getAddress());
            preparedStatement.setDouble(3, order.getCost());
            preparedStatement.setTimestamp(4, order.getDate());
            preparedStatement.setLong(5, order.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    @Override
    public Optional<Order> get(Long id) {
        ConnectionPool connection = POOL.getConnection();
        ResultSet resultSet = null;
        Order order = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_ORDER_BY_ID)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                order = createOrder(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> getAll() {
        ConnectionPool connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_ALL_ORDERS)) {
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Order order = createOrder(resultSet);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return orders;
    }

    @Override
    public boolean updateOrder(Long id, Map<Product, Integer> products) {
        OrderProductsDAO orderProductsDAO = OrderProductsDAOImpl.getInstance();
        orderProductsDAO.delete(id);
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            OrderProducts orderProducts = OrderProducts.createOrderProducts(id, product.getId());
            orderProductsDAO.save(orderProducts);
        }
        return true;
    }

    @Override
    public boolean updateOrderStatus(Long id, OrderStatus status) {
        ConnectionPool connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.UPDATE_ORDER_STATUS_BY_ID)) {
            preparedStatement.setString(1, status.name());
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    @Override
    public List<Order> getAllUserOrders(Long id) {
        ConnectionPool connection = POOL.getConnection();
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
            e.printStackTrace();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return orders;
    }

    @Override
    public List<Order> getAllUncompletedOrders(Long id) {
        ConnectionPool connection = POOL.getConnection();
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
            e.printStackTrace();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return orders;
    }

    @Override
    public Map<Product, Integer> getAllProductsByOrderId(Long id) {
        ConnectionPool connection = POOL.getConnection();
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
            e.printStackTrace();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        Map<Product, Integer> map = new HashMap<>();
        if (productsId.size() > 0) {
            ProductDAO productDAO = ProductDAOImpl.getInstance();
            for (Long productId : productsId) {
                Optional<Product> optionalProduct = productDAO.get(productId);
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    Integer value = map.get(product);
                    if (value == null || value == 0) {
                        value = 1;
                    } else {
                        value++;
                    }
                    map.put(product, value);
                }
            }
        }
        return map;
    }

    private Order createOrder(final ResultSet resultSet) throws SQLException {
        return Order.createOrder(resultSet.getLong("id"), resultSet.getLong("user_id"),
                OrderStatus.parseStatus(resultSet.getString("status")), resultSet.getString("address"),
                Payment.parsePayment(resultSet.getString("payment")), resultSet.getDouble("cost"),
                resultSet.getTimestamp("date"), getAllProductsByOrderId(resultSet.getLong("id")));
    }
}
