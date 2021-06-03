package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.OrderStatus;
import com.example.restaurant.model.Order;
import com.example.restaurant.model.User;
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
import java.util.List;
import java.util.Optional;

@WebServlet(name = "account", urlPatterns = "/account")
public class AccountServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(AccountServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        OrderService orderService = OrderServiceImpl.getInstance();
        List<Order> orders = orderService.getUserOrders(user.getId());

        String removeOrder = req.getParameter("removeOrder");
        if (removeOrder != null) {
            Long id = null;
            try {
                id = Long.parseLong(removeOrder);
            } catch (NumberFormatException e) {
                LOGGER.warn("Can't parse id to delete order");
            }
            if (id != null) {
                Long finalId = id;
                Optional<Order> optionalOrder = orders.stream().filter(order -> order.getId().equals(finalId)).findAny();
                if (optionalOrder.isPresent()) {
                    Order order = optionalOrder.get();
                    if (order.getStatus() != OrderStatus.PREPARING && order.getStatus() != OrderStatus.DELIVERING) {
                        boolean result = orderService.deleteOrder(id);
                        if (result) {
                            orders.removeIf(order1 -> order1.getId().equals(finalId));
                        } else {
                            String locale = (String) session.getAttribute("locale");
                            PrintWriter out = resp.getWriter();
                            if (locale != null && locale.equals("ru_UA")) {
                                out.println("<script type=\"text/javascript\">");
                                out.println("alert('Произошла ошибка при удалении заказа! " +
                                        "Пожалуйста, попробуйте снова если заказ не удалился (Номер заказа: " + finalId + ")!');");
                                out.println("location.href='account';");
                                out.println("</script>");
                            } else {
                                out.println("<script type=\"text/javascript\">");
                                out.println("alert('An error occurred while deleting the order! " +
                                        "Please try again, if the order is not deleted (Order ID: " + finalId + ")!');");
                                out.println("location.href='account';");
                                out.println("</script>");
                            }
                        }
                    } else {
                        LOGGER.warn("Can't delete order due to current order status");
                    }
                } else {
                    LOGGER.warn("Can't find order by id. ID: " + finalId);
                }
            }
        }
        resp.sendRedirect("account");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");
        OrderService orderService = OrderServiceImpl.getInstance();
        List<Order> orders = orderService.getUserOrders(user.getId());
        session.setAttribute("allUserOrders", orders);

        req.getRequestDispatcher("account.jsp").forward(req, resp);
    }
}
