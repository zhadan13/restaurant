package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.MenuCategories;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet mapping home page.
 * This servlet handles all changes at home page and show product list with details.
 *
 * @author Zhadan Artem
 * @see HttpServlet
 */

@WebServlet(name = "home", urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(HomeServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * Method shows product list according to category and sorting option.
     * Also method implementing pagination and handle adding/removing operations on products from menu.
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException {@inheritDoc}
     * @throws IOException      {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        ProductService productService = ProductServiceImpl.getInstance();
        List<Product> products = productService.getAllProducts();

        // Getting category from request, and save it in session to remember user's filter
        String categoryFromRequest = req.getParameter("category");
        String categoryFromSession = (String) session.getAttribute("category");
        if (categoryFromRequest != null) {
            categoryFromSession = categoryFromRequest;
        } else {
            if (categoryFromSession != null) {
                categoryFromRequest = categoryFromSession;
            } else {
                categoryFromSession = MenuCategories.DEFAULT.name();
                categoryFromRequest = MenuCategories.DEFAULT.name();
            }
        }
        session.setAttribute("category", categoryFromSession);

        // Getting sorting from request, and save it in session to remember user's sorting
        String sortFromRequest = req.getParameter("sorting");
        String sortFromSession = (String) session.getAttribute("sorting");
        if (sortFromRequest != null) {
            sortFromSession = sortFromRequest;
        } else {
            if (sortFromSession != null) {
                sortFromRequest = sortFromSession;
            } else {
                sortFromSession = "DEFAULT";
                sortFromRequest = "DEFAULT";
            }
        }
        session.setAttribute("sorting", sortFromSession);

        // Filtering products in menu by selected category and sorting option
        products = MenuCategories.filter(products, categoryFromRequest, sortFromRequest);

        // Pagination
        String elementsPerPage = req.getParameter("elementsPerPage");
        String pageIndex = req.getParameter("pageIndex");
        int elementsPerPageValue = Util.DEFAULT_ELEMENTS_PER_PAGE;
        int pageIndexValue = 1;
        if (elementsPerPage != null && !elementsPerPage.equals("")) {
            elementsPerPageValue = Integer.parseInt(elementsPerPage);
        }
        if (pageIndex != null && !pageIndex.equals("")) {
            pageIndexValue = Integer.parseInt(pageIndex);
        }
        int numberOfPages = products.size() / elementsPerPageValue;
        if (products.size() % elementsPerPageValue != 0) {
            numberOfPages++;
        }
        List<Product> productsForCurrentPage = new ArrayList<>();
        for (int i = (pageIndexValue - 1) * elementsPerPageValue; i < pageIndexValue * elementsPerPageValue; i++) {
            if (i < products.size()) {
                productsForCurrentPage.add(products.get(i));
            }
        }
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("elementsPerPage", elementsPerPageValue);
        req.setAttribute("pageIndex", pageIndexValue);

        session.setAttribute("products", productsForCurrentPage);

        // Adding or removing products from cart
        Map<Long, Integer> productsInBucket = (Map<Long, Integer>) session.getAttribute("productsInBucket");
        if (productsInBucket == null) {
            productsInBucket = new HashMap<>();
        }
        String productAddId = req.getParameter("addProduct");
        String productRemoveId = req.getParameter("removeProduct");
        if (productAddId != null || productRemoveId != null) {
            if (productAddId != null) {
                Long id = null;
                try {
                    id = Long.parseLong(productAddId);
                } catch (NumberFormatException e) {
                    LOGGER.warn("Can't parse id product to add", e);
                }
                if (id != null) {
                    productsInBucket.merge(id, 1, Integer::sum);
                }
            }
            if (productRemoveId != null) {
                Long id = null;
                try {
                    id = Long.parseLong(productRemoveId);
                } catch (NumberFormatException e) {
                    LOGGER.warn("Can't parse id product to add", e);
                }
                if (id != null) {
                    productsInBucket.remove(id);
                }
            }
            session.setAttribute("productsInBucket", productsInBucket);
            resp.sendRedirect("home");
        } else {
            session.setAttribute("productsInBucket", productsInBucket);
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        }
    }
}
