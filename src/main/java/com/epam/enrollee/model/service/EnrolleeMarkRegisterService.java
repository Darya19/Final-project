package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;

import java.util.Map;
import java.util.Optional;

/**
 * The interface Enrollee mark register service.
 * The interface defines methods for working with the enrollee mark register object.
 * Interface extends base service.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public interface EnrolleeMarkRegisterService extends BaseService {

    /**
     * Find enrollee mark register optional.
     * Find all subjects and mark which belongs enrollee.
     *
     * @param enrolleeId the enrollee id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<EnrolleeMarkRegister> findEnrolleeMarkRegister(int enrolleeId) throws ServiceException;

    /**
     * Update enrollee marks optional.
     * Update marks for items that were previously selected.
     *
     * @param enrolleeId the enrollee id
     * @param parameters the parameters
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<EnrolleeMarkRegister> updateEnrolleeMarks(int enrolleeId, Map<String, String> parameters) throws ServiceException;

    /**
     * Update enrollee register optional.
     * Change the selected items and marks for them.
     *
     * @param enrolleId    the enrolle id
     * @param markRegister the mark register
     * @param parameters   the parameters
     * @return the optional
     * @throws ServiceException the service exception
     */
    public Optional<EnrolleeMarkRegister> updateEnrolleeRegister(int enrolleId, EnrolleeMarkRegister markRegister,
                                                                 Map<String, String> parameters) throws ServiceException;
}
