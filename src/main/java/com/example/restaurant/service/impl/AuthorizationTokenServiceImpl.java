package com.example.restaurant.service.impl;

import com.example.restaurant.db.dao.AuthorizationTokenDAO;
import com.example.restaurant.db.dao.impl.AuthorizationTokenDAOImpl;
import com.example.restaurant.model.AuthorizationToken;
import com.example.restaurant.service.AuthorizationTokenService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

/**
 * Authorization Token Service implementation.
 *
 * @author Zhadan Artem
 * @see AuthorizationToken
 * @see AuthorizationTokenService
 */

public class AuthorizationTokenServiceImpl implements AuthorizationTokenService {
    private static final Logger LOGGER = LogManager.getLogger(AuthorizationTokenServiceImpl.class);

    /**
     * Singleton instance.
     */
    private static AuthorizationTokenServiceImpl INSTANCE;

    /**
     * Constructs an <b>AuthorizationTokenServiceImpl</b>.
     */
    private AuthorizationTokenServiceImpl() {

    }

    /**
     * Returns already created instance of <b>AuthorizationTokenServiceImpl</b>, or creates new and then returns.
     *
     * @return {@link AuthorizationTokenServiceImpl} instance
     */
    public static AuthorizationTokenServiceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (AuthorizationTokenServiceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AuthorizationTokenServiceImpl();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AuthorizationToken> saveToken(AuthorizationToken authorizationToken) {
        AuthorizationTokenDAO authorizationTokenDAO = AuthorizationTokenDAOImpl.getInstance();
        Optional<AuthorizationToken> optional = authorizationTokenDAO.save(authorizationToken);
        if (optional.isPresent()) {
            LOGGER.info("Service: Successfully saved authorization token");
        } else {
            LOGGER.error("Service: Can't save authorization token");
        }
        return optional;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteToken(Long id) {
        AuthorizationTokenDAO authorizationTokenDAO = AuthorizationTokenDAOImpl.getInstance();
        boolean result = authorizationTokenDAO.delete(id);
        if (result) {
            LOGGER.info("Service: Successfully deleted authorization token");
        } else {
            LOGGER.error("Service: Can't delete authorization token");
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateToken(AuthorizationToken authorizationToken) {
        AuthorizationTokenDAO authorizationTokenDAO = AuthorizationTokenDAOImpl.getInstance();
        boolean result = authorizationTokenDAO.update(authorizationToken);
        if (result) {
            LOGGER.info("Service: Successfully updated authorization token");
        } else {
            LOGGER.error("Service: Can't update authorization token");
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AuthorizationToken> getToken(Long id) {
        AuthorizationTokenDAO authorizationTokenDAO = AuthorizationTokenDAOImpl.getInstance();
        Optional<AuthorizationToken> optional = authorizationTokenDAO.get(id);
        if (optional.isPresent()) {
            LOGGER.info("Service: Successfully got authorization token");
        } else {
            LOGGER.error("Service: Can't get authorization token");
        }
        return optional;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AuthorizationToken> getAllTokens() {
        AuthorizationTokenDAO authorizationTokenDAO = AuthorizationTokenDAOImpl.getInstance();
        return authorizationTokenDAO.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<AuthorizationToken> getUserToken(Long id) {
        AuthorizationTokenDAO authorizationTokenDAO = AuthorizationTokenDAOImpl.getInstance();
        Optional<AuthorizationToken> optional = authorizationTokenDAO.getByUserId(id);
        if (optional.isPresent()) {
            LOGGER.info("Service: Successfully got authorization token by user id");
        } else {
            LOGGER.error("Service: Can't get authorization token by user id");
        }
        return optional;
    }
}
