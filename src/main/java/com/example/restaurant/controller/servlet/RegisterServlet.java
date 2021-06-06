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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * Servlet mapping registration page.
 *
 * @author Zhadan Artem
 * @see HttpServlet
 */

@WebServlet(name = "register", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    /**
     * Method controlling and checking register form.
     * According to entered information to the form performing registration or showing error alert.
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException {@inheritDoc}
     * @throws IOException      {@inheritDoc}
     * @see com.example.restaurant.util.UserValidator
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String phoneNumber = req.getParameter("phoneNumber");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserService userService = UserServiceImpl.getInstance();
        User userForRegister = User.createUser(email, password.toCharArray(), phoneNumber, name, Role.USER, false);
        Optional<User> optionalUser = userService.registration(userForRegister);
        if (optionalUser.isPresent()) {
            resp.sendRedirect("login");
        } else {
            String locale = (String) req.getSession().getAttribute("locale");
            PrintWriter out = resp.getWriter();
            if (locale != null && locale.equals("ru_UA")) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Введены неверные данные или введенные почта/телефон уже зарегистрированы! " +
                        "Проверьте введенные данные! " +
                        "(Пароль должен содержать не менее 8 символов: одну цифру, одну букву в нижнем и верхнем регистре)');");
                out.println("location.href='register';");
                out.println("</script>");
            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Not valid information or entered email/phone has been already registered! " +
                        "Check this out! " +
                        "(Password must contain at least 8 chars: one digit, one letter lowercase and one letter uppercase)');");
                out.println("location.href='register';");
                out.println("</script>");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
