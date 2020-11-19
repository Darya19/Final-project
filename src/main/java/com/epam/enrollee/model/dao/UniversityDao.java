package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface University dao.
 * The interface defines methods for working with the specialty and faculty tables in database.
 * Interface extends base service.
 *
 * @param <T> the type parameter
 * @author Darya Shcherbina
 * @version 1.0
 */
public interface UniversityDao<T> extends BaseDao<T> {

    /**
     * Update status by id boolean.
     *
     * @param facultyId the faculty id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateStatusById(int facultyId) throws DaoException;

    /**
     * Update boolean.
     *
     * @param parameters the parameters
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean update(Map<String, Object> parameters) throws DaoException;

    /**
     * Find by enrollee id optional.
     *
     * @param enrolleeId the enrollee id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> findByEnrolleeId(int enrolleeId) throws DaoException;

    /**
     * Find considered enrollee id by id list.
     *
     * @param facultyId the faculty id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Integer> findConsideredEnrolleeIdById(int facultyId) throws DaoException;

}