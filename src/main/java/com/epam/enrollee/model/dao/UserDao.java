package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.entity.User;

import java.util.Optional;

/**
 * The interface User dao.
 * The interface defines methods for working with the enrollee table in database.
 * Interface extends base service.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public interface UserDao extends BaseDao<User> {

    /**
     * Find user by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByEmail(String email) throws DaoException;

    /**
     * Find password by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<String> findPasswordByEmail(String email) throws DaoException;
}