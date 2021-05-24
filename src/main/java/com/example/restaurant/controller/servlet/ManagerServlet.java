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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        if (!filterFromRequest.equalsIgnoreCase("DEFAULT")) {
            if (filterFromRequest.equalsIgnoreCase(OrderStatus.ACCEPTED.name())) {
                orders = orders.stream()
                        .filter(order -> order.getStatus().name().equalsIgnoreCase(OrderStatus.ACCEPTED.name()))
                        .collect(Collectors.toList());
            } else if (filterFromRequest.equalsIgnoreCase(OrderStatus.CONFIRMED.name())) {
                orders = orders.stream()
                        .filter(order -> order.getStatus().name().equalsIgnoreCase(OrderStatus.CONFIRMED.name()))
                        .collect(Collectors.toList());
            } else if (filterFromRequest.equalsIgnoreCase(OrderStatus.PREPARING.name())) {
                orders = orders.stream()
                        .filter(order -> order.getStatus().name().equalsIgnoreCase(OrderStatus.PREPARING.name()))
                        .collect(Collectors.toList());
            } else if (filterFromRequest.equalsIgnoreCase(OrderStatus.DELIVERING.name())) {
                orders = orders.stream()
                        .filter(order -> order.getStatus().name().equalsIgnoreCase(OrderStatus.DELIVERING.name()))
                        .collect(Collectors.toList());
            } else if (filterFromRequest.equalsIgnoreCase(OrderStatus.COMPLETED.name())) {
                orders = orders.stream()
                        .filter(order -> order.getStatus().name().equalsIgnoreCase(OrderStatus.COMPLETED.name()))
                        .collect(Collectors.toList());
            } else if (filterFromRequest.equalsIgnoreCase(OrderStatus.REJECTED.name())) {
                orders = orders.stream()
                        .filter(order -> order.getStatus().name().equalsIgnoreCase(OrderStatus.REJECTED.name()))
                        .collect(Collectors.toList());
            } else if (filterFromRequest.equalsIgnoreCase("UNCOMPLETED")) {
                orders = orders.stream()
                        .filter(order -> order.getStatus().ordinal() != 5 && order.getStatus().ordinal() != 4)
                        .collect(Collectors.toList());
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

        if (!sortFromRequest.equals("default")) {
            if (sortFromRequest.equals("user id")) {
                orders.sort(Comparator.comparing(Order::getUserId));
            } else if (sortFromRequest.equals("status")) {
                orders.sort(Comparator.comparing(Order::getStatus));
            } else if (sortFromRequest.equals("date from old to new")) {
                orders.sort(Comparator.comparing(Order::getDate));
            } else if (sortFromRequest.equals("date from new to old")) {
                orders.sort(Comparator.comparing(Order::getDate).reversed());
            }
        }

        session.setAttribute("sorting", sortFromSession);

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
                long id;
                id = Long.parseLong(productRemoveParameter);
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
