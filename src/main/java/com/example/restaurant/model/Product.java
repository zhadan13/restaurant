package com.example.restaurant.model;

import com.example.restaurant.constants.MenuCategories;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Serializable {
    private static final long serialVersionUID = -2527253164384388237L;

    private Long id;
    private String name;
    private String details;
    private MenuCategories category;
    private Double price;
    private Double weight;
    private Integer popularity;

    private Product() {
    }

    private Product(String name, String details, MenuCategories category, Double price, Double weight, Integer popularity) {
        this.name = name;
        this.details = details;
        this.category = category;
        this.price = price;
        this.weight = weight;
        this.popularity = popularity;
    }

    private Product(Long id, String name, String details, MenuCategories category, Double price, Double weight, Integer popularity) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.category = category;
        this.price = price;
        this.weight = weight;
        this.popularity = popularity;
    }

    public static Product createProduct() {
        return new Product();
    }

    public static Product createProduct(String name, String details, MenuCategories category, Double price, Double weight, Integer popularity) {
        return new Product(name, details, category, price, weight, popularity);
    }

    public static Product createProduct(Long id, String name, String details, MenuCategories category, Double price, Double weight, Integer popularity) {
        return new Product(id, name, details, category, price, weight, popularity);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public MenuCategories getCategory() {
        return category;
    }

    public void setCategory(MenuCategories category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name)
                && Objects.equals(details, product.details) && Objects.equals(category, product.category)
                && Objects.equals(price, product.price) && Objects.equals(weight, product.weight)
                && Objects.equals(popularity, product.popularity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, details, category, price, weight, popularity);
    }

    @Override
    public String toString() {
        return "Product { " +
                "id = " + id +
                ", name = '" + name + '\'' +
                ", details = '" + details + '\'' +
                ", category = '" + category + '\'' +
                ", price = " + price +
                ", weight = " + weight +
                ", popularity = " + popularity +
                '}';
    }
}
