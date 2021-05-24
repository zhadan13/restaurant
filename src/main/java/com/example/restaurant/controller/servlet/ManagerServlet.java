package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.constants.Util;
import com.example.restaurant.model.Order;
import com.example.restaurant.service.OrderService;
import com.example.restaurant.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "admin", urlPatterns = "/admin")
public class ManagerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        OrderService orderService = OrderServiceImpl.getInstance();
        List<Order> orders = orderService.getAllOrders();

        String filterFromRequest = req.getParameter("filter");
        String filterFromSession = (String) session.getAttribute("filter");
        if (filterFromRequest != null) {
            filterFromSession = filterFromRequest;
        } else {
            if (filterFromSession != null) {
                filterFromRequest = filterFromSession;
            } else {
                filterFromSession = "DEFAULT";
                filterFromRequest = "DEFAULT";
            }
        }
        session.setAttribute("filter", filterFromSession);

        String sortFromRequest = req.getParameter("sorting");
        String sortFromSession = (String) session.getAttribute("sorting");
        if (sortFromRequest != null) {
            sortFromSession = sortFromRequest;
        } else {
            if (sortFromSession != null) {
                sortFromRequest = sortFromSession;
            } else {
                sortFromSession = "default";
                sortFromRequest = "default";
            }
        }
        session.setAttribute("sorting", sortFromSession);

        orders = OrderStatus.filter(orders, filterFromRequest, sortFromRequest);

        String elementsPerPage = req.getParameter("elementsPerPage");
        String pageIndex = req.getParameter("pageIndex");
        int elementsPerPageValue = Util.DEFAULT_ELEMENTS_PER_PAGE_FOR_MANAGER;
        int pageIndexValue = 1;
        if (elementsPerPage != null && !elementsPerPage.equals("")) {
            elementsPerPageValue = Integer.parseInt(elementsPerPage);
        }
        if (pageIndex != null && !pageIndex.equals("")) {
            pageIndexValue = Integer.parseInt(pageIndex);
        }
        int numberOfPages = orders.size() / elementsPerPageValue;
        if (orders.size() % elementsPerPageValue != 0) {
            numberOfPages++;
        }
        List<Order> ordersForCurrentPage = new ArrayList<>();
        for (int i = (pageIndexValue - 1) * elementsPerPageValue; i < pageIndexValue * elementsPerPageValue; i++) {
            if (i < orders.size()) {
                ordersForCurrentPage.add(orders.get(i));
            }
        }
        req.setAttribute("numberOfPages", numberOfPages);
        req.setAttribute("elementsPerPage", elementsPerPageValue);
        req.setAttribute("pageIndex", pageIndexValue);

        String changeStatusId = req.getParameter("changeStatusFor");
        String changeStatusTo = req.getParameter("newStatus");
        String productRemoveParameter = req.getParameter("removeOrder");
        if ((changeStatusId != null && changeStatusTo != null) || productRemoveParameter != null) {
            if (productRemoveParameter != null) {
                long id = Long.parseLong(productRemoveParameter);
                orderService.deleteOrder(id);
                orders.removeIf(order -> order.getId().equals(id));
            } else {
                orderService.updateOrderStatus(Long.parseLong(changeStatusId), OrderStatus.parseStatus(changeStatusTo));
            }
            session.setAttribute("orders", ordersForCurrentPage);
            resp.sendRedirect("/admin");
        } else {
            session.setAttribute("orders", ordersForCurrentPage);
            req.getRequestDispatcher("/admin.jsp").forward(req, resp);
        }
    }
}
