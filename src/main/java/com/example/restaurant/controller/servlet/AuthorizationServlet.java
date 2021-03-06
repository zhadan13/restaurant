package com.example.restaurant.controller.servlet;

import com.example.restaurant.model.AuthorizationToken;
import com.example.restaurant.model.User;
import com.example.restaurant.service.AuthorizationTokenService;
import com.example.restaurant.service.UserService;
import com.example.restaurant.service.impl.AuthorizationTokenServiceImpl;
import com.example.restaurant.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Servlet mapping authorization page.
 * This servlet mapping page for email verification and updates verification status for user.
 *
 * @author Zhadan Artem
 * @see HttpServlet
 */

@WebServlet(name = "authorization", urlPatterns = "/authorization")
public class AuthorizationServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(AuthorizationServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    /**
     * Method handles request to authorization page and update verification status for user.
     * This method ignores incorrect request attributes and if attributes are correct,
     * compare requested token with token from database and update verification status.
     *
     * @param req  HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException {@inheritDoc}
     * @throws IOException      {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("user");
        Long id = null;
        try {
            id = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            LOGGER.warn("Can't parse user id", e);
        }
        String token = req.getParameter("token");

        if (id == null || token == null) {
            LOGGER.error("Token or id is null");
            resp.sendRedirect("confirmEmailError.jsp");
        } else {
            AuthorizationTokenService ats = AuthorizationTokenServiceImpl.getInstance();
            Optional<AuthorizationToken> optionalToken = ats.getUserToken(id);
            if (optionalToken.isPresent()) {
                AuthorizationToken authorizationToken = optionalToken.get();
                if (authorizationToken.getToken().equals(token)) {
                    UserService userService = UserServiceImpl.getInstance();
                    Optional<User> optionalUser = userService.getUser(id);
                    if (optionalUser.isPresent() && !optionalUser.get().getAuthorized()) {
                        boolean result = userService.updateAuthorizationStatus(id);
                        if (result) {
                            LOGGER.info("User email successfully verified. User id: " + id);
                            resp.sendRedirect("confirmEmail.jsp");
                        } else {
                            LOGGER.error("Can't update authorization status for user. User id: " + id);
                            resp.sendRedirect("confirmEmailError.jsp");
                        }
                    } else {
                        LOGGER.error("Can't get user or user email already verified. User id: " + id);
                        resp.sendRedirect("confirmEmailError.jsp");
                    }
                } else {
                    LOGGER.error("Tokens not equals");
                    resp.sendRedirect("confirmEmailError.jsp");
                }
            } else {
                LOGGER.error("Can't get token. User id: " + id);
                resp.sendRedirect("confirmEmailError.jsp");
            }
        }
    }
}
