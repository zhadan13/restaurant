package com.example.restaurant.service;

import com.example.restaurant.model.AuthorizationToken;

import java.util.List;
import java.util.Optional;

/**
 * Interface for a Authorization token.
 *
 * @author Zhadan Artem
 * @see AuthorizationToken
 */

public interface AuthorizationTokenService {
    /**
     * Performing saving authorizationToken.
     *
     * @param authorizationToken {@link AuthorizationToken} token, which has to be saved
     * @return {@link Optional<AuthorizationToken>#of(Object)} if save success,
     * or {@link Optional<AuthorizationToken>#empty()} otherwise
     * @see AuthorizationToken
     */
    Optional<AuthorizationToken> saveToken(AuthorizationToken authorizationToken);

    /**
     * Performing authorizationToken delete.
     *
     * @param id authorizationToken's identifier
     * @return <tt>true</tt> if delete success, or <tt>false</tt> otherwise
     * @see AuthorizationToken
     */
    boolean deleteToken(Long id);

    /**
     * Performing authorizationToken update.
     *
     * @param authorizationToken {@link AuthorizationToken} token, which has to be updated
     * @return <tt>true</tt> if update success, or <tt>false</tt> otherwise
     * @see AuthorizationToken
     */
    boolean updateToken(AuthorizationToken authorizationToken);

    /**
     * Getting authorizationToken.
     *
     * @param id authorizationToken's identifier
     * @return {@link Optional<AuthorizationToken>#of(Object)} if get success,
     * or {@link Optional<AuthorizationToken>#empty()} otherwise
     * @see AuthorizationToken
     */
    Optional<AuthorizationToken> getToken(Long id);

    /**
     * Getting all authorizationTokens.
     *
     * @return {@link List} of authorizationTokens
     * @see AuthorizationToken
     */
    List<AuthorizationToken> getAllTokens();

    /**
     * Getting user's authorizationToken.
     *
     * @param id user's identifier
     * @return {@link Optional<AuthorizationToken>#of(Object)} if get success,
     * or {@link Optional<AuthorizationToken>#empty()} otherwise
     * @see AuthorizationToken
     */
    Optional<AuthorizationToken> getUserToken(Long id);
}
