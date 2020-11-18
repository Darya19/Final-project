package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Subject;

import java.util.List;
import java.util.Optional;

/**
 * The interface Enrollee mark register dao.
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
     * Update boolean.
     *
     * @param register          the register
     * @param enrolleeId        the enrollee id
     * @param subjectsForUpdate the subjects for update
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean update(EnrolleeMarkRegister register, int enrolleeId, List<Subject> subjectsForUpdate) throws DaoException;

    /**
     * Find enrollee mark register by enrollee id optional.
     *
     * @param enrolleeId the enrollee id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<EnrolleeMarkRegister> findEnrolleeMarkRegisterByEnrolleeId(int enrolleeId) throws DaoException;

}
