package com.example.restaurant.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entity class for user's authorization token.
 *
 * @author Zhadan Artem
 * @see Serializable
 */

public class AuthorizationToken implements Serializable {
    private static final long serialVersionUID = 7309036786780438521L;

    private Long id;
    private Long userId;
    private String token;

    /**
     * Constructs an <b>AuthorizationToken</b> without parameters.
     */
    private AuthorizationToken() {

    }

    /**
     * Constructs an <b>AuthorizationToken</b> with <tt>token</tt> and <tt>userId</tt> parameters.
     *
     * @param token  user's token
     * @param userId user's identifier
     * @see User
     */
    private AuthorizationToken(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    /**
     * Constructs an <b>AuthorizationToken</b> with <tt>id</tt>, <tt>token</tt> and <tt>userId</tt> parameters.
     *
     * @param id     identifier
     * @param token  user's token
     * @param userId user's identifier
     * @see User
     */
    private AuthorizationToken(Long id, String token, Long userId) {
        this.id = id;
        this.token = token;
        this.userId = userId;
    }

    /**
     * Fabric method for creating new <b>AuthorizationToken</b>.
     *
     * @return {@link AuthorizationToken}
     */
    public static AuthorizationToken createToken() {
        return new AuthorizationToken();
    }

    /**
     * Fabric method for creating new <b>AuthorizationToken</b>.
     *
     * @param token  user's token
     * @param userId user's identifier
     * @return {@link AuthorizationToken}
     * @see User
     */
    public static AuthorizationToken createToken(String token, Long userId) {
        return new AuthorizationToken(token, userId);
    }

    /**
     * Fabric method for creating new <b>AuthorizationToken</b>.
     *
     * @param id     identifier
     * @param token  user's token
     * @param userId user's identifier
     * @return {@link AuthorizationToken}
     * @see User
     */
    public static AuthorizationToken createToken(Long id, Long userId, String token) {
        return new AuthorizationToken(id, token, userId);
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthorizationToken that = (AuthorizationToken) o;
        return Objects.equals(id, that.id) && Objects.equals(token, that.token) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, userId);
    }

    @Override
    public String toString() {
        return "AuthorizationToken { " +
                "id = " + id +
                ", token = '" + token + '\'' +
                ", userId = " + userId +
                '}';
    }
}
