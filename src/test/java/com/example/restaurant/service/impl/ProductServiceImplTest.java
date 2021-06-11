package com.example.restaurant.service.impl;

import com.example.restaurant.db.dao.impl.ProductDAOImpl;
import com.example.restaurant.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @InjectMocks
    Product product;

    @InjectMocks
    ProductServiceImpl service;

    @Mock(lenient = true)
    ProductDAOImpl dao;

    MockedStatic<ProductDAOImpl> mocked;

    @BeforeEach
    void init() {
        mocked = mockStatic(ProductDAOImpl.class);
        mocked.when(ProductDAOImpl::getInstance).thenReturn(dao);
        mocked.when(() -> dao.save(any())).thenReturn(Optional.of(product));
        mocked.when(() -> dao.delete(anyLong())).thenReturn(true);
        mocked.when(() -> dao.updatePopularity(anyLong(), anyInt())).thenReturn(true);
    }

    @AfterEach
    void close() {
        mocked.close();
    }

    @Test
    void saveProduct() {
        // when
        Optional<Product> optional = service.saveProduct(product);

        // then
        assertNotEquals(Optional.empty(), optional);
    }

    @Test
    void deleteProduct() {
        assertTrue(service.deleteProduct(1L));
    }

    @Test
    void updateProduct() {
        assertFalse(service.updateProduct(product));
    }

    @Test
    void updateProductPopularity() {
        assertTrue(service.updateProductPopularity(1L, 10));
    }

    @Test
    void getProduct() {
        // when
        Optional<Product> optional = service.getProduct(1L);

        // then
        assertEquals(Optional.empty(), optional);
    }

    @Test
    void getAllProducts() {
        // when
        List<Product> products = service.getAllProducts();

        // then
        assertEquals(new ArrayList<>(), products);
    }
}
