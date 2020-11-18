package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Passport;

import java.util.List;
import java.util.Optional;

/**
 * The interface Enrollee dao.
 */
public interface EnrolleeDao extends BaseDao<Enrollee> {

    /**
     * Update enrollee boolean.
     *
     * @param enrollee the enrollee
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateEnrollee(Enrollee enrollee) throws DaoException;

    /**
     * Update passport boolean.
     *
     * @param passport the passport
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updatePassport(Passport passport) throws DaoException;

    /**
     * Update enrollee faculty boolean.
     *
     * @param enrollee the enrollee
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateEnrolleeFaculty(Enrollee enrollee) throws DaoException;

    /**
     * Update enrollee specialty boolean.
     *
     * @param enrollee the enrollee
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateEnrolleeSpecialty(Enrollee enrollee) throws DaoException;

    /**
     * Find enrollee by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Enrollee> findEnrolleeByEmail(String email) throws DaoException;

    /**
     * Find passport by enrollee id optional.
     *
     * @param enrolleeId the enrollee id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Passport> findPassportByEnrolleeId(int enrolleeId) throws DaoException;

    /**
     * Find unarchived enrollee by specialty id list.
     *
     * @param specialtyId the specialty id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Enrollee> findUnarchivedEnrolleeBySpecialtyId(int specialtyId) throws DaoException;

    /**
     * Update application status by enrollee id boolean.
     *
     * @param enrolleeId        the enrollee id
     * @param applicationStatus the application status
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateApplicationStatusByEnrolleeId(int enrolleeId, String applicationStatus) throws DaoException;
}
