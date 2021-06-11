package com.example.restaurant.constants;

import com.example.restaurant.model.Product;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.example.restaurant.constants.MenuCategories.*;
import static org.junit.jupiter.api.Assertions.*;

class MenuCategoriesTest {
    @Test
    void parseCategory() {
        assertEquals(DRINKS, MenuCategories.parseCategory("drinks"));
    }

    @Test
    void parseCategoryWrong() {
        assertNull(MenuCategories.parseCategory("WOK"));
    }

    @Test
    void filter() {
        // given
        List<Product> products = new ArrayList<>();
        products.add(Product.createProduct("Name1", "Details1", DRINKS, 200.0, 500.0, 30));
        products.add(Product.createProduct("Name2", "Details2", SUSHI, 250.0, 600.0, 31));
        products.add(Product.createProduct("Name3", "Details3", DRINKS, 160.0, 700.0, 32));
        products.add(Product.createProduct("Name4", "Details4", PIZZA, 150.0, 800.0, 33));
        products.add(Product.createProduct("Name5", "Details5", DRINKS, 180.0, 900.0, 34));

        // when
        products = MenuCategories.filter(products, "drinks", "PRICE HIGH TO LOW");

        // then
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(Product.createProduct("Name1", "Details1", DRINKS, 200.0, 500.0, 30));
        expectedProducts.add(Product.createProduct("Name5", "Details5", DRINKS, 180.0, 900.0, 34));
        expectedProducts.add(Product.createProduct("Name3", "Details3", DRINKS, 160.0, 700.0, 32));
        assertEquals(products, expectedProducts);
    }

    @Test
    void filterDefault() {
        // given
        List<Product> products = new ArrayList<>();
        products.add(Product.createProduct("Name1", "Details1", DRINKS, 200.0, 500.0, 30));
        products.add(Product.createProduct("Name2", "Details2", SUSHI, 250.0, 600.0, 31));
        products.add(Product.createProduct("Name3", "Details3", DRINKS, 160.0, 700.0, 32));
        products.add(Product.createProduct("Name4", "Details4", PIZZA, 150.0, 800.0, 33));
        products.add(Product.createProduct("Name5", "Details5", DRINKS, 180.0, 900.0, 34));

        // when
        products = MenuCategories.filter(products, "default", "default");

        // then
        List<Product> expectedProducts = new ArrayList<>();
        expectedProducts.add(Product.createProduct("Name1", "Details1", DRINKS, 200.0, 500.0, 30));
        expectedProducts.add(Product.createProduct("Name2", "Details2", SUSHI, 250.0, 600.0, 31));
        expectedProducts.add(Product.createProduct("Name3", "Details3", DRINKS, 160.0, 700.0, 32));
        expectedProducts.add(Product.createProduct("Name4", "Details4", PIZZA, 150.0, 800.0, 33));
        expectedProducts.add(Product.createProduct("Name5", "Details5", DRINKS, 180.0, 900.0, 34));
        assertEquals(products, expectedProducts);
    }
}
