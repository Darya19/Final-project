package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.entity.Subject;

import java.util.List;

/**
 * The interface Subject dao.
 * The interface defines methods for working with the subject table in database.
 * Interface extends base service.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public interface SubjectDao extends BaseDao<Subject> {

    /**
     * Find subjects by specialty id list.
     *
     * @param specialtyId the specialty id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Subject> findSubjectsBySpecialtyId(int specialtyId) throws DaoException;
}