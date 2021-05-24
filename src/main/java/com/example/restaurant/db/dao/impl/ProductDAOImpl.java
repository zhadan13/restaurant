package com.example.restaurant.db.dao.impl;

import com.example.restaurant.constants.MenuCategories;
import com.example.restaurant.constants.SQLQuery;
import com.example.restaurant.db.connection_pool.ConnectionPool;
import com.example.restaurant.db.connection_pool.Pool;
import com.example.restaurant.db.dao.ProductDAO;
import com.example.restaurant.model.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAOImpl implements ProductDAO {
    private static final Logger LOGGER = LogManager.getLogger(ProductDAOImpl.class);

    private static ProductDAOImpl INSTANCE;
    private static final Pool POOL = Pool.getInstance();

    private ProductDAOImpl() {

    }

    public static ProductDAOImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (ProductDAOImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ProductDAOImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Optional<Product> save(Product product) {
        ConnectionPool connection = POOL.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.INSERT_NEW_PRODUCT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDetails());
            preparedStatement.setString(3, product.getCategory().name());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setDouble(5, product.getWeight());
            preparedStatement.setInt(6, product.getPopularity());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long field = resultSet.getLong(1);
                product.setId(field);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't save product", e);
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.of(product);
    }

    @Override
    public boolean delete(Long id) {
        ConnectionPool connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.DELETE_PRODUCT_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't delete product", e);
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    @Override
    public boolean update(Product product) {
        ConnectionPool connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.UPDATE_PRODUCT_BY_ID)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDetails());
            preparedStatement.setString(3, product.getCategory().name());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setDouble(5, product.getWeight());
            preparedStatement.setInt(6, product.getPopularity());
            preparedStatement.setLong(7, product.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't update product", e);
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    @Override
    public Optional<Product> get(Long id) {
        ConnectionPool connection = POOL.getConnection();
        ResultSet resultSet = null;
        Product product = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_PRODUCT_BY_ID)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                product = createProduct(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get product", e);
            return Optional.empty();
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return Optional.ofNullable(product);
    }

    @Override
    public List<Product> getAll() {
        ConnectionPool connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<Product> products = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.GET_ALL_PRODUCTS)) {
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Product product = createProduct(resultSet);
                products.add(product);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get all products", e);
        } finally {
            POOL.closeResources(resultSet);
            POOL.releaseConnection(connection);
        }
        return products;
    }

    @Override
    public boolean updatePopularity(Long id, Integer popularity) {
        ConnectionPool connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.UPDATE_PRODUCT_POPULARITY_BY_ID)) {
            preparedStatement.setInt(1, popularity);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't update product popularity", e);
            return false;
        } finally {
            POOL.releaseConnection(connection);
        }
        return true;
    }

    private Product createProduct(final ResultSet resultSet) throws SQLException {
        return Product.createProduct(resultSet.getLong("id"), resultSet.getString("name"),
                resultSet.getString("details"), MenuCategories.parseCategory(resultSet.getString("category")),
                resultSet.getDouble("price"), resultSet.getDouble("weight"),
                resultSet.getInt("popularity"));
    }
}
