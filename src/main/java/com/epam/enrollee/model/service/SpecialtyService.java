package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Specialty;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Specialty service.
 * The interface defines methods for working with the specialty object.
 * Interface extends base service.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public interface SpecialtyService extends BaseService {

    /**
     * Remove boolean.
     *
     * @param specialtyId the specialty id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean remove(String specialtyId) throws ServiceException;

    /**
     * Find all open specialties list.
     * Find all specialties where recruitment open.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Specialty> findAllOpenSpecialties() throws ServiceException;

    /**
     * Find enrollee specialty.
     * Find the faculty to which the enrollee has sent an application.
     *
     * @param enrolleeId the enrollee id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Specialty> findEnrolleeSpecialty(int enrolleeId) throws ServiceException;

    /**
     * Update boolean.
     * Update specialty name, recruitment status and number of seats.
     *
     * @param parameters the parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean update(Map<String, String> parameters) throws ServiceException;

    /**
     * Find open specialties of faculty list.
     * Find all  specialties which belong the given faculty and has open recruitment.
     *
     * @param facultyId the faculty id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Specialty> findOpenSpecialtiesOfFaculty(String facultyId) throws ServiceException;

    /**
     * Find all applications by specialty list.
     * Find all specialties which belong the given faculty
     * where recruitment is open.
     *
     * @param specialtyId the specialty id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Integer> findAllApplicationsBySpecialty(String specialtyId) throws ServiceException;

    /**
     * Find active specialties of faculty list.
     * Find all enrollee who sent application to this specialty.
     *
     * @param facultyId the faculty id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Specialty> findActiveSpecialtiesOfFaculty(String facultyId) throws ServiceException;

    /**
     * Find specialty by id optional.
     *
     * @param specialtyId the specialty id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Specialty> findSpecialtyById(String specialtyId) throws ServiceException;

    /**
     * Change specialty recruitment boolean.
     *
     * @param specialtyId the specialty id
     * @param recruitment the recruitment
     * @param application the application
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean changeSpecialtyRecruitment(String specialtyId, String recruitment, List<Integer> application) throws ServiceException;

    /**
     * Check considered applications boolean.
     * Check if this specialty has not reviewed applications.
     *
     * @param specialtyId the specialty id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean checkConsideredApplications(String specialtyId) throws ServiceException;
}
