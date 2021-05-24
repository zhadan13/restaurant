package com.example.restaurant.constants;

import com.example.restaurant.model.Product;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum MenuCategories {
    DEFAULT, PIZZA, SUSHI, SALAD, PASTA, DESSERT, DRINKS;

    public static MenuCategories parseCategory(final String category) {
        if (DEFAULT.name().equalsIgnoreCase(category)) {
            return DEFAULT;
        }
        if (PIZZA.name().equalsIgnoreCase(category)) {
            return PIZZA;
        }
        if (SUSHI.name().equalsIgnoreCase(category)) {
            return SUSHI;
        }
        if (SALAD.name().equalsIgnoreCase(category)) {
            return SALAD;
        }
        if (PASTA.name().equalsIgnoreCase(category)) {
            return PASTA;
        }
        if (DESSERT.name().equalsIgnoreCase(category)) {
            return DESSERT;
        }
        if (DRINKS.name().equalsIgnoreCase(category)) {
            return DRINKS;
        }
        return null;
    }

    public static List<Product> filter(List<Product> products, String category, String sorting) {
        if (!category.equalsIgnoreCase(MenuCategories.DEFAULT.name())) {
            if (category.equalsIgnoreCase(MenuCategories.PIZZA.name())) {
                products = products
                        .stream()
                        .filter(product -> product.getCategory() == MenuCategories.PIZZA)
                        .collect(Collectors.toList());
            } else if (category.equalsIgnoreCase(MenuCategories.SUSHI.name())) {
                products = products
                        .stream()
                        .filter(product -> product.getCategory() == MenuCategories.SUSHI)
                        .collect(Collectors.toList());
            } else if (category.equalsIgnoreCase(MenuCategories.SALAD.name())) {
                products = products
                        .stream()
                        .filter(product -> product.getCategory() == MenuCategories.SALAD)
                        .collect(Collectors.toList());
            } else if (category.equalsIgnoreCase(MenuCategories.PASTA.name())) {
                products = products
                        .stream()
                        .filter(product -> product.getCategory() == MenuCategories.PASTA)
                        .collect(Collectors.toList());
            } else if (category.equalsIgnoreCase(MenuCategories.DESSERT.name())) {
                products = products
                        .stream()
                        .filter(product -> product.getCategory() == MenuCategories.DESSERT)
                        .collect(Collectors.toList());
            } else if (category.equalsIgnoreCase(MenuCategories.DRINKS.name())) {
                products = products
                        .stream()
                        .filter(product -> product.getCategory() == MenuCategories.DRINKS)
                        .collect(Collectors.toList());
            }
        }
        if (!sorting.equalsIgnoreCase("DEFAULT")) {
            if ("CATEGORY".equalsIgnoreCase(sorting)) {
                products.sort(Comparator.comparing(Product::getCategory));
            } else if ("NAME".equalsIgnoreCase(sorting)) {
                products.sort(Comparator.comparing(Product::getName));
            } else if ("POPULARITY".equalsIgnoreCase(sorting)) {
                products.sort(Comparator.comparing(Product::getPopularity).reversed());
            } else if ("PRICE LOW TO HIGH".equalsIgnoreCase(sorting)) {
                products.sort(Comparator.comparing(Product::getPrice));
            } else if ("PRICE HIGH TO LOW".equalsIgnoreCase(sorting)) {
                products.sort(Comparator.comparing(Product::getPrice).reversed());
            }
        }
        return products;
    }
}
