package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.User;

import java.util.Optional;

/**
 * The interface User service.
 * The interface defines methods for working with the user object.
 * Interface extends base service.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public interface UserService extends BaseService {

    /**
     * Check email and password boolean.
     *
     * @param email    the email
     * @param password the password
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean checkEmailAndPassword(String email, String password) throws ServiceException;

    /**
     * Find user by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUserByEmail(String email) throws ServiceException;
}
