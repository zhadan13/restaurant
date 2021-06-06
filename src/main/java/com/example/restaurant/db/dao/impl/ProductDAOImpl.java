package com.example.restaurant.db.dao.impl;

import com.example.restaurant.constants.MenuCategories;
import com.example.restaurant.db.connection_pool.ConnectionImpl;
import com.example.restaurant.db.dao.DAO;
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

import static com.example.restaurant.constants.SQLQuery.*;

/**
 * Product Data Access Object implementation.
 *
 * @author Zhadan Artem
 * @see ProductDAO
 * @see DAO
 * @see Product
 */

public class ProductDAOImpl implements ProductDAO {
    private static final Logger LOGGER = LogManager.getLogger(ProductDAOImpl.class);

    /**
     * Singleton instance.
     */
    private static ProductDAOImpl INSTANCE;

    /**
     * Constructs an <b>ProductDAOImpl</b>.
     */
    private ProductDAOImpl() {

    }

    /**
     * Returns already created instance of <b>ProductDAOImpl</b>, or creates new and then returns.
     *
     * @return {@link ProductDAOImpl} instance
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Product> save(Product product) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_PRODUCT, PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(true);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDetails());
            preparedStatement.setString(3, product.getCategory().name());
            preparedStatement.setDouble(4, product.getPrice());
            preparedStatement.setDouble(5, product.getWeight());
            preparedStatement.setInt(6, product.getPopularity());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                product.setId(id);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_BY_ID)) {
            connection.setAutoCommit(true);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Product product) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_BY_ID)) {
            connection.setAutoCommit(true);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Product> get(Long id) {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        Product product = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_BY_ID)) {
            connection.setAutoCommit(true);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> getAll() {
        ConnectionImpl connection = POOL.getConnection();
        ResultSet resultSet = null;
        List<Product> products = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_PRODUCTS)) {
            connection.setAutoCommit(true);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updatePopularity(Long id, Integer popularity) {
        ConnectionImpl connection = POOL.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_POPULARITY_BY_ID)) {
            connection.setAutoCommit(true);
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

    /**
     * Method mapping <b>ResultSet</b> to <b>Product</b>.
     *
     * @param resultSet resultSet which should be mapped
     * @return {@link Product}
     * @throws SQLException {@inheritDoc}
     */
    private Product createProduct(final ResultSet resultSet) throws SQLException {
        return Product.createProduct(resultSet.getLong("id"), resultSet.getString("name"),
                resultSet.getString("details"), MenuCategories.parseCategory(resultSet.getString("category")),
                resultSet.getDouble("price"), resultSet.getDouble("weight"),
                resultSet.getInt("popularity"));
    }
}
