package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.Util;
import com.example.restaurant.model.Product;
import com.example.restaurant.service.ProductService;
import com.example.restaurant.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "cartServlet", urlPatterns = "/cart")
public class CartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Map<Long, Integer> productsInBucket = (Map<Long, Integer>) session.getAttribute("productsInBucket");
        if (productsInBucket == null) {
            productsInBucket = new HashMap<>();
        }

        String productRemoveParameter = req.getParameter("removeProduct");
        if (productRemoveParameter != null) {
            long id = Long.parseLong(productRemoveParameter);
            productsInBucket.remove(id);
            session.setAttribute("productsInBucket", productsInBucket);
            resp.sendRedirect("/cart");
        } else {
            ProductService productService = ProductServiceImpl.getInstance();
            Map<Product, Integer> bucket = new HashMap<>();
            productsInBucket.forEach((aLong, integer) -> bucket.put(productService.getProduct(aLong).orElseThrow(RuntimeException::new), integer));
            double totalPrice = bucket.entrySet()
                    .stream()
                    .mapToDouble(value -> value.getValue() * value.getKey().getPrice())
                    .sum();
            if (totalPrice < Util.DEFAULT_FREE_DELIVERY) {
                session.setAttribute("deliveryPrice", 50.0);
            } else {
                session.setAttribute("deliveryPrice", 0.0);
            }
            session.setAttribute("bucket", bucket);
            session.setAttribute("totalPrice", totalPrice);

            req.getRequestDispatcher("/cart.jsp").forward(req, resp);
        }
    }
}
