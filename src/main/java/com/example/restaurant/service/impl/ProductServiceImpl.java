package com.example.restaurant.service.impl;

import com.example.restaurant.db.dao.ProductDAO;
import com.example.restaurant.db.dao.impl.ProductDAOImpl;
import com.example.restaurant.model.Product;
import com.example.restaurant.service.ProductService;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {
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
        return productDAO.save(product);
    }

    @Override
    public boolean deleteProduct(Long id) {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        return productDAO.delete(id);
    }

    @Override
    public boolean updateProduct(Product product) {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        return productDAO.update(product);
    }

    @Override
    public boolean updateProductPopularity(Long id, Integer popularity) {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        return productDAO.updatePopularity(id, popularity);
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        return productDAO.get(id);
    }

    @Override
    public List<Product> getAllProducts() {
        ProductDAO productDAO = ProductDAOImpl.getInstance();
        return productDAO.getAll();
    }
}
