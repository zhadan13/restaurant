package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.MenuCategories;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "home", urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        ProductService productService = ProductServiceImpl.getInstance();
        List<Product> products = productService.getAllProducts();

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

        products = MenuCategories.filter(products, categoryFromRequest, sortFromRequest);

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

        Map<Long, Integer> productsInBucket = (Map<Long, Integer>) session.getAttribute("productsInBucket");
        if (productsInBucket == null) {
            productsInBucket = new HashMap<>();
        }
        String productAddParameter = req.getParameter("addProduct");
        String productRemoveParameter = req.getParameter("removeProduct");
        if (productAddParameter != null || productRemoveParameter != null) {
            if (productAddParameter != null) {
                long id = Long.parseLong(productAddParameter);
                productsInBucket.merge(id, 1, Integer::sum);
            }
            if (productRemoveParameter != null) {
                long id = Long.parseLong(productRemoveParameter);
                productsInBucket.remove(id);
            }
            session.setAttribute("productsInBucket", productsInBucket);
            resp.sendRedirect("/home");
        } else {
            session.setAttribute("productsInBucket", productsInBucket);
            req.getRequestDispatcher("/home.jsp").forward(req, resp);
        }
    }
}
