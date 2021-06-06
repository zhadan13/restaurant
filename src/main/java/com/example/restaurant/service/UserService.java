package com.example.restaurant.service;

import com.example.restaurant.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface for a User Service.
 *
 * @author Zhadan Artem
 * @see User
 */

public interface UserService {
    /**
     * Performing user validation and registration.
     *
     * @param user {@link User} user, which has to be registered
     * @return {@link Optional<User>#of(Object)} if register success, or {@link Optional<User>#empty()} otherwise
     * @see User
     */
    Optional<User> registration(User user);

    /**
     * Performing user validation and authorization.
     *
     * @param email    user's email
     * @param password user's password
     * @return {@link Optional<User>#of(Object)} if authorization success, or {@link Optional<User>#empty()} otherwise
     * @see User
     */
    Optional<User> authorization(String email, String password);

    /**
     * Performing user validation.
     *
     * @param user {@link User} user, which has to be validated
     * @return <tt>true</tt> if user information valid, or <tt>false</tt> otherwise
     * @see User
     * @see com.example.restaurant.util.UserValidator
     */
    boolean validation(User user);

    /**
     * Performing user validation and update.
     *
     * @param user {@link User} user, which has to be updated
     * @return <tt>true</tt> if update success, or <tt>false</tt> otherwise
     * @see User
     */
    boolean updateUser(User user);

    /**
     * Performing user information validation and update.
     *
     * @param user {@link User} user, which has to be updated
     * @return <tt>true</tt> if update success, or <tt>false</tt> otherwise
     * @see User
     */
    boolean updateUserInformation(User user);

    /**
     * Performing user authorization status update.
     *
     * @param id user's identifier
     * @return <tt>true</tt> if update success, or <tt>false</tt> otherwise
     * @see User
     */
    boolean updateAuthorizationStatus(Long id);

    /**
     * Getting user by id.
     *
     * @param id user's identifier
     * @return {@link Optional<User>#of(Object)} if get success, or {@link Optional<User>#empty()} otherwise
     * @see User
     */
    Optional<User> getUser(Long id);

    /**
     * Getting user by email.
     *
     * @param email user's email
     * @return {@link Optional<User>#of(Object)} if get success, or {@link Optional<User>#empty()} otherwise
     * @see User
     */
    Optional<User> getUser(String email);

    /**
     * Getting all users.
     *
     * @return {@link List} of users
     * @see User
     */
    List<User> getAllUsers();

    /**
     * Performing user delete.
     *
     * @param id user's identifier
     * @return <tt>true</tt> if delete success, or <tt>false</tt> otherwise
     * @see User
     */
    boolean deleteUser(Long id);
}
