package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.Util;
import com.example.restaurant.model.Product;
import com.example.restaurant.service.ProductService;
import com.example.restaurant.service.impl.ProductServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet mapping cart page.
 * This servlet handles all changes at cart page and show user's cart with details.
 *
 * @author Zhadan Artem
 * @see HttpServlet
 */

@WebServlet(name = "cart", urlPatterns = "/cart")
public class CartServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(CartServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * Method updating cart and show details.
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException {@inheritDoc}
     * @throws IOException      {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Map<Long, Integer> productsInBucket = (Map<Long, Integer>) session.getAttribute("productsInBucket");
        if (productsInBucket == null) {
            productsInBucket = new HashMap<>();
        }

        String removeId = req.getParameter("removeProduct");
        if (removeId != null) {
            Long id = null;
            try {
                id = Long.parseLong(removeId);
            } catch (NumberFormatException e) {
                LOGGER.warn("Can't parse order id", e);
            }
            if (id != null) {
                productsInBucket.remove(id);
            }
            session.setAttribute("productsInBucket", productsInBucket);
            resp.sendRedirect("cart");
        } else {
            ProductService productService = ProductServiceImpl.getInstance();
            Map<Product, Integer> bucket = new HashMap<>();
            productsInBucket.forEach((aLong, integer) -> productService.getProduct(aLong).ifPresent(product -> bucket.put(product, integer)));
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

            req.getRequestDispatcher("cart.jsp").forward(req, resp);
        }
    }
}
