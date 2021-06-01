package com.example.restaurant.service.impl;

import com.example.restaurant.db.dao.ProductDAO;
import com.example.restaurant.db.dao.impl.ProductDAOImpl;
import com.example.restaurant.model.Product;
import com.example.restaurant.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LogManager.getLogger(ProductServiceImpl.class);

    private static ProductServiceImpl INSTANCE;

    private ProductServiceImpl() {

    }

    public static ProductServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (ProductServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ProductServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Optional<Product> saveProduct(Product product) {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        Optional<Product> optionalProduct = productDAO.save(product);
        if (optionalProduct.isPresent()) {
            LOGGER.info("Service: Successfully saved product. Product id: " + product.getId());
        } else {
            LOGGER.error("Service: Can't save product. Product id: " + product.getId());
        }
        return optionalProduct;
    }

    @Override
    public boolean deleteProduct(Long id) {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        boolean result = productDAO.delete(id);
        if (result) {
            LOGGER.info("Service: Successfully deleted product. Product id: " + id);
        } else {
            LOGGER.error("Service: Can't delete product. Product id: " + id);
        }
        return result;
    }

    @Override
    public boolean updateProduct(Product product) {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        boolean result = productDAO.update(product);
        if (result) {
            LOGGER.info("Service: Successfully updated product. Product id: " + product.getId());
        } else {
            LOGGER.error("Service: Can't update product. Product id: " + product.getId());
        }
        return result;
    }

    @Override
    public boolean updateProductPopularity(Long id, Integer popularity) {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        boolean result = productDAO.updatePopularity(id, popularity);
        if (result) {
            LOGGER.info("Service: Successfully updated product popularity. Product id: " + id);
        } else {
            LOGGER.error("Service: Can't update product popularity. Product id: " + id);
        }
        return result;
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        Optional<Product> optionalProduct = productDAO.get(id);
        if (optionalProduct.isPresent()) {
            LOGGER.info("Service: Successfully got product. Product id: " + id);
        } else {
            LOGGER.error("Service: Can't get product. Product id: " + id);
        }
        return optionalProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        return productDAO.getAll();
    }
}
