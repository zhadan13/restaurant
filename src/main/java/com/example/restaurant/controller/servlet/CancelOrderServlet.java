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
import java.io.PrintWriter;

/**
 * Servlet mapping cancelOrder page.
 * This servlet sets status of order to REJECTED when user cancel it.
 *
 * @author Zhadan Artem
 * @see HttpServlet
 */

@WebServlet(name = "cancelOrder", urlPatterns = "/cancelOrder")
public class CancelOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        session.removeAttribute("totalPrice");
        session.removeAttribute("productsInBucket");
        session.removeAttribute("bucket");
        Order order = (Order) session.getAttribute("order");
        OrderService orderService = OrderServiceImpl.getInstance();
        boolean result = orderService.updateOrderStatus(order.getId(), OrderStatus.REJECTED);
        if (!result) {
            String locale = (String) session.getAttribute("locale");
            PrintWriter out = resp.getWriter();
            if (locale != null && locale.equals("ru_UA")) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Произошла ошибка при отмене заказа! " +
                        "Пожалуйста, проверьте ваш аккаунт и если заказ не отменен - отмените его снова (Номер заказа: " + order.getId().toString() + ")!');");
                out.println("location.href='account';");
                out.println("</script>");
            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Sorry, an error occurred while canceling the order! " +
                        "Please check your account page and if the order is not deleted (Order ID: " + order.getId().toString() + "), try to cancel it again!');");
                out.println("location.href='account';");
                out.println("</script>");
            }
        }
        session.removeAttribute("order");

        resp.sendRedirect(Util.APPLICATION_NAME);
    }
}
