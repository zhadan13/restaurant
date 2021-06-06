package com.example.restaurant.controller.servlet;

import com.example.restaurant.constants.Role;
import com.example.restaurant.model.User;
import com.example.restaurant.service.UserService;
import com.example.restaurant.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * Servlet mapping login page.
 *
 * @author Zhadan Artem
 * @see HttpServlet
 */

@WebServlet(name = "login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    /**
     * Method controlling and checking login form.
     * According to entered information to the form performing authorization or showing error alert.
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException {@inheritDoc}
     * @throws IOException      {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String locale = (String) session.getAttribute("locale");

        String email = req.getParameter("email");
        String password = req.getParameter("password");
        UserService userService = UserServiceImpl.getInstance();
        Optional<User> optionalUser = userService.authorization(email, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getAuthorized()) {
                session.setAttribute("user", user);
                if (user.getRole() == Role.USER) {
                    resp.sendRedirect("home");
                } else {
                    resp.sendRedirect("admin");
                }
            } else {
                PrintWriter out = resp.getWriter();
                if (locale != null && locale.equals("ru_UA")) {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Электронная почта не подтверждена! Пожалуйста, проверьте свой почтовый ящик и подтвердите регистрацию!');");
                    out.println("location.href='login';");
                    out.println("</script>");
                } else {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Email address not confirmed! Please check your mailbox and confirm it!');");
                    out.println("location.href='login';");
                    out.println("</script>");
                }
            }
        } else {
            PrintWriter out = resp.getWriter();
            if (locale != null && locale.equals("ru_UA")) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Электронная почта или пароль введены неверно! Проверьте правильность данных!');");
                out.println("location.href='login';");
                out.println("</script>");
            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Email or Password incorrect! Check this out!');");
                out.println("location.href='login';");
                out.println("</script>");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
