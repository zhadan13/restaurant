package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.Payment;
import com.example.restaurant.model.Order;
import com.example.restaurant.model.Product;
import com.example.restaurant.model.User;
import com.example.restaurant.service.OrderService;
import com.example.restaurant.service.ProductService;
import com.example.restaurant.service.impl.OrderServiceImpl;
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
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static com.example.restaurant.constants.OrderStatus.*;

/**
 * Servlet mapping order page.
 * This servlet processing order information and register new order.
 *
 * @author Zhadan Artem
 * @see HttpServlet
 */

@WebServlet(name = "order", urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(OrderServlet.class);

    /**
     * Method-handler for processing information about order from input form and creating new order if data valid.
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException {@inheritDoc}
     * @throws IOException      {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String address = req.getParameter("address");
        String dateTime = req.getParameter("time");
        int paymentIndex = Integer.parseInt(req.getParameter("payment"));
        User user = (User) session.getAttribute("user");
        Double cost = (Double) session.getAttribute("totalPrice");
        Double deliveryPrice = (Double) session.getAttribute("deliveryPrice");
        Map<Product, Integer> bucket = (Map<Product, Integer>) session.getAttribute("bucket");
        Payment payment = Payment.CASH;
        if (paymentIndex == 2) {
            payment = Payment.CARD_ONLINE;
        }
        if (paymentIndex == 3) {
            payment = Payment.CARD_OFFLINE;
        }

        OrderService orderService = OrderServiceImpl.getInstance();
        Order orderForRegister = Order.createOrder(user.getId(), ACCEPTED, address, payment, cost + deliveryPrice, LocalDateTime.parse(dateTime), bucket);
        Optional<Order> optionalOrder = orderService.saveOrder(orderForRegister);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            session.setAttribute("order", order);
            if (payment == Payment.CARD_ONLINE) {
                resp.sendRedirect("payment");
            } else {
                order.setStatus(CONFIRMED);
                orderService.updateOrderStatus(order.getId(), CONFIRMED);
                ProductService productService = ProductServiceImpl.getInstance();
                order.getProducts().keySet().forEach(product -> {
                    boolean result = productService.updateProductPopularity(product.getId(), product.getPopularity() + 1);
                    if (!result) {
                        LOGGER.warn("Can't update product popularity. Product id: " + product.getId());
                    }
                });
                session.setAttribute("order", order);
                resp.sendRedirect("successOrder?orderId=" + order.getId());
            }
        } else {
            String locale = (String) session.getAttribute("locale");
            PrintWriter out = resp.getWriter();
            if (locale != null && locale.equals("ru_UA")) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Введенные данные не валидны! Проверьте данные!');");
                out.println("location.href='order';");
                out.println("</script>");
            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Not valid order information! Check this out!');");
                out.println("location.href='order';");
                out.println("</script>");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("order.jsp").forward(req, resp);
    }
}
