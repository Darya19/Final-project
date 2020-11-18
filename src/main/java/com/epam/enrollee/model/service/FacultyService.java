package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Faculty;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Faculty service.
 * The interface defines methods for working with the faculty object.
 * Interface extends base service.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public interface FacultyService extends BaseService {

    /**
     * Remove faculty and its specialties boolean.
     *
     * @param facultyId the faculty id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean removeFacultyAndItsSpecialties(String facultyId) throws ServiceException;

    /**
     * Check considered applications boolean.
     * Check if this faculty has not reviewed applications.
     *
     * @param facultyId the faculty id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean checkConsideredApplications(String facultyId) throws ServiceException;

    /**
     * Update boolean.
     * Update faculty name, faculty description.
     *
     * @param parameters the parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean update(Map<String, String> parameters) throws ServiceException;

    /**
     * Find faculty by id optional.
     *
     * @param facultyId the faculty id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Faculty> findFacultyById(String facultyId) throws ServiceException;

    /**
     * Find all active faculties list.
     * Find all faculties which have active status.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Faculty> findAllActiveFaculties() throws ServiceException;

    /**
     * Find enrollee faculty optional.
     * Find the faculty to which the enrollee has sent an application.
     *
     * @param enrolleeId the enrollee id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Faculty> findEnrolleeFaculty(int enrolleeId) throws ServiceException;
}
