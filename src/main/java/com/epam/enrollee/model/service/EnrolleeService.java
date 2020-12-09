package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Passport;
import com.epam.enrollee.model.entity.Specialty;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Enrollee service.
 * The interface defines methods for working with the enrollee object.
 * Interface extends base service.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public interface EnrolleeService extends BaseService {

    /**
     * Find enrollee by email optional.
     * Find enrollee object by email.
     *
     * @param email the email
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Enrollee> findEnrolleeByEmail(String email) throws ServiceException;

    /**
     * Find enrollee passport optional.
     * Find the passport object that belongs to the user.
     *
     * @param enrolleeId the enrollee id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Passport> findEnrolleePassport(int enrolleeId) throws ServiceException;

    /**
     * Update enrollee name information optional.
     * Update enrollee first name, last name and middle name.
     *
     * @param enrollee   the enrollee
     * @param parameters the parameters
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Enrollee> updateEnrolleeNameInformation(Enrollee enrollee, Map<String, String> parameters)
            throws ServiceException;

    /**
     * Update enrollee passport information optional.
     * Update the passport series and number and personal number that belongs to the user.
     *
     * @param passport   the passport
     * @param parameters the parameters
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Passport> updateEnrolleePassportInformation(Passport passport, Map<String, String> parameters)
            throws ServiceException;

    /**
     * Update enrollee faculty boolean.
     * Update faculty in enrollee application.
     *
     * @param enrollee    the enrollee
     * @param specialtyId the specialty id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateEnrolleeFaculty(Enrollee enrollee, String specialtyId) throws ServiceException;

    /**
     * Update enrollee specialty boolean.
     * Update specialty in enrollee application.
     *
     * @param enrollee    the enrollee
     * @param specialtyId the specialty id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateEnrolleeSpecialty(Enrollee enrollee, String specialtyId) throws ServiceException;

    /**
     * Remove boolean.
     * Remove enrollee from database.
     *
     * @param parameters the parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean remove(Map<String, Object> parameters) throws ServiceException;

    /**
     * Find all unarchived enrollees on specialty list.
     *
     * @param specialtyId the specialty id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Enrollee> findAllUnarchivedEnrolleesOnSpecialty(String specialtyId) throws ServiceException;

    /**
     * Change application status boolean.
     * Change application status from considered to rejected or accepted.
     *
     * @param enrolleeId the enrollee id
     * @param status     the status
     * @param specialty  the specialty
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean changeApplicationStatus(String enrolleeId, String status, Specialty specialty) throws ServiceException;

    /**
     * Update enrollee application optional.
     * Change the selected items and marks for them.
     *
     * @param enrollee   the enrollee
     * @param parameters the parameters
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<EnrolleeMarkRegister> updateEnrolleeApplication(Enrollee enrollee, Map<String, String> parameters)
            throws ServiceException;
}