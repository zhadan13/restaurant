package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.constants.Util;
import com.example.restaurant.model.Order;
import com.example.restaurant.service.OrderService;
import com.example.restaurant.service.impl.OrderServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "admin", urlPatterns = "/admin")
public class ManagerServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(ManagerServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String locale = (String) session.getAttribute("locale");

        OrderService orderService = OrderServiceImpl.getInstance();
        List<Order> orders = orderService.getAllOrders();

        String removeOrder = req.getParameter("removeOrder");
        if (removeOrder != null) {
            Long id = null;
            try {
                id = Long.parseLong(removeOrder);
            } catch (NumberFormatException e) {
                LOGGER.warn("Can't parse id to delete order");
            }
            if (id != null) {
                boolean result = orderService.deleteOrder(id);
                if (result) {
                    Long finalId = id;
                    orders.removeIf(order -> order.getId().equals(finalId));
                } else {
                    PrintWriter out = resp.getWriter();
                    if (locale != null && locale.equals("ru_UA")) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Произошла ошибка при удалении заказа! " +
                                "Попробуйте снова, если заказ не удалился (Номер заказа: " + removeOrder + ")!');");
                        out.println("location.href='admin';");
                        out.println("</script>");
                    } else {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('An error occurred while deleting the order! " +
                                "Please try again, if the order is not deleted (Order ID: " + removeOrder + ")!');");
                        out.println("location.href='admin';");
                        out.println("</script>");
                    }
                }
            }
        }
        resp.sendRedirect("admin");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String locale = (String) req.getSession().getAttribute("locale");

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
                sortFromSession = "DEFAULT";
                sortFromRequest = "DEFAULT";
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
        if (changeStatusId != null && changeStatusTo != null) {
            Long id = null;
            try {
                id = Long.parseLong(changeStatusId);
            } catch (NumberFormatException e) {
                LOGGER.warn("Can't parse order id to change status", e);
            }
            OrderStatus status = OrderStatus.parseStatus(changeStatusTo);
            if (id != null && status != null) {
                boolean result = orderService.updateOrderStatus(Long.parseLong(changeStatusId), OrderStatus.parseStatus(changeStatusTo));
                if (!result) {
                    PrintWriter out = resp.getWriter();
                    if (locale != null && locale.equals("ru_UA")) {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('Произошла ошибка при изменении статуса заказа! " +
                                "Попробуйте снова, если статус заказа не поменялся (Номер заказа: " + changeStatusId + ")!');");
                        out.println("location.href='admin';");
                        out.println("</script>");
                    } else {
                        out.println("<script type=\"text/javascript\">");
                        out.println("alert('An error occurred while deleting the order! " +
                                "Please try again, if the order is not deleted (Order ID: " + changeStatusId + ")!');");
                        out.println("location.href='admin';");
                        out.println("</script>");
                    }
                }
            } else {
                LOGGER.warn("Can't parse parameters to change order status");
            }
            session.setAttribute("orders", ordersForCurrentPage);
            resp.sendRedirect("admin");
        } else {
            session.setAttribute("orders", ordersForCurrentPage);
            req.getRequestDispatcher("admin.jsp").forward(req, resp);
        }
    }
}
