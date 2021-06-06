package com.example.restaurant.db.dao;

import com.example.restaurant.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface for a User Data Access Object.
 *
 * @author Zhadan Artem
 * @see DAO
 * @see User
 */

public interface UserDAO extends DAO<User> {
    /**
     * {@inheritDoc}
     */
    @Override
    Optional<User> save(User user);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean delete(Long id);

    /**
     * {@inheritDoc}
     */
    @Override
    boolean update(User user);

    /**
     * {@inheritDoc}
     */
    @Override
    Optional<User> get(Long id);

    /**
     * {@inheritDoc}
     */
    @Override
    List<User> getAll();

    /**
     * Updating user information in database.
     * Method can be used to update user information (without password).
     * If entity null, or row with entity id does not exists in table returns <tt>false</tt>.
     * If an <tt>SQLException</tt> occurs during the operation, returns <tt>false</tt>.
     * Otherwise it performs updating and returns <tt>true</tt>.
     *
     * @param user {@link User} object, which has to be updated
     * @return <tt>true</tt> if update operation success, or <tt>false</tt> otherwise
     * @see User
     */
    boolean updateInfo(User user);

    /**
     * Updating user authorization status in database by user identifier.
     * If identifier null, or row with this id does not exists in table returns <tt>false</tt>.
     * If an <tt>SQLException</tt> occurs during the operation, returns <tt>false</tt>.
     * Otherwise it performs updating and returns <tt>true</tt>.
     *
     * @param id     identifier of user, which has to be updated
     * @param status status to be changed to
     * @return <tt>true</tt> if update operation success, or <tt>false</tt> otherwise
     * @see User
     */
    boolean updateAuthorizationStatus(Long id, Boolean status);

    /**
     * Performing user registration.
     * Checks if user with unique parameters not exists in database, and after that, saves user to db.
     *
     * @param user {@link User} object, which has to be registered
     * @return {@link Optional<User>#of(Object)} if register operation success,
     * or {@link Optional<User>#empty()} otherwise
     * @see User
     * @see #checkIfUserExistsByUniqueParameters(User)
     * @see #save(User)
     */
    Optional<User> registration(User user);

    /**
     * Performing user authorization.
     * Getting user by email, after that verifying user password.
     * Returns <tt>Optional.of(user)</tt> id email and password correct, or <tt>Optional.empty()</tt> otherwise.
     *
     * @param email    user email
     * @param password user password
     * @return {@link Optional<User>#of(Object)} if authorization operation success,
     * or {@link Optional<User>#empty()} otherwise
     * @see User
     * @see #getByEmail(String)
     */
    Optional<User> authorization(String email, String password);

    /**
     * Checking if user exists in database by unique parameters.
     *
     * @param user {@link User} object, which has to be checked
     * @return <tt>true</tt> if check operation success, or <tt>false</tt> otherwise
     * @see User
     */
    boolean checkIfUserExistsByUniqueParameters(User user);

    /**
     * Checking if user exists in database by email and password.
     *
     * @param email    user email
     * @param password user password
     * @return <tt>true</tt> if check operation success, or <tt>false</tt> otherwise
     * @see User
     */
    boolean checkIfUserExistsByEmailAndPassword(String email, String password);

    /**
     * Getting user from database by email.
     *
     * @param email user email
     * @return {@link Optional<User>#of(Object)} if get operation success, or {@link Optional<User>#empty()} otherwise
     */
    Optional<User> getByEmail(String email);
}
