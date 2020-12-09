package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;

import java.util.Optional;

/**
 * The interface Enrollee mark register dao.
 * The interface defines methods for working with the mark table in database.
 * Interface extends base service.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public interface EnrolleeMarkRegisterDao {

    /**
     * Update boolean.
     *
     * @param register   the register
     * @param enrolleeId the enrollee id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean update(EnrolleeMarkRegister register, int enrolleeId) throws DaoException;

    /**
     * Find enrollee mark register by enrollee id optional.
     *
     * @param enrolleeId the enrollee id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<EnrolleeMarkRegister> findEnrolleeMarkRegisterByEnrolleeId(int enrolleeId) throws DaoException;

}