package com.example.restaurant.constants;

import com.example.restaurant.model.Product;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enum with possible categories from menu.
 *
 * @author Zhadan Artem
 */

public enum MenuCategories {
    DEFAULT, PIZZA, SUSHI, SALAD, PASTA, DESSERT, DRINKS;

    /**
     * Returns the enum value corresponding to the string representation, or <tt>null</tt> if string representation is incorrect.
     *
     * @param category the string value of enum value
     * @return {@link MenuCategories} value, or <tt>null</tt> if category string can't parse to enum value
     */
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

    /**
     * Returns list filtered by category and sorted products.
     *
     * @param products the list of {@link Product} to be filtered and sorted
     * @param category the string category by which the list will be filtered
     * @param sorting  the string parameter by which the list will be sorted
     * @return filtered list
     * @see Product
     */
    public static List<Product> filter(List<Product> products, final String category, final String sorting) {
        if (!category.equalsIgnoreCase(DEFAULT.name())) {
            if (category.equalsIgnoreCase(PIZZA.name())) {
                products = products
                        .stream()
                        .filter(product -> product.getCategory() == PIZZA)
                        .collect(Collectors.toList());
            } else if (category.equalsIgnoreCase(SUSHI.name())) {
                products = products
                        .stream()
                        .filter(product -> product.getCategory() == SUSHI)
                        .collect(Collectors.toList());
            } else if (category.equalsIgnoreCase(SALAD.name())) {
                products = products
                        .stream()
                        .filter(product -> product.getCategory() == SALAD)
                        .collect(Collectors.toList());
            } else if (category.equalsIgnoreCase(PASTA.name())) {
                products = products
                        .stream()
                        .filter(product -> product.getCategory() == PASTA)
                        .collect(Collectors.toList());
            } else if (category.equalsIgnoreCase(DESSERT.name())) {
                products = products
                        .stream()
                        .filter(product -> product.getCategory() == DESSERT)
                        .collect(Collectors.toList());
            } else if (category.equalsIgnoreCase(DRINKS.name())) {
                products = products
                        .stream()
                        .filter(product -> product.getCategory() == DRINKS)
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
