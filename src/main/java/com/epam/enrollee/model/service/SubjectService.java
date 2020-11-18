package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Subject;

import java.util.List;

/**
 * The interface Subject service.
 * The interface defines methods for working with the subject object.
 * Interface extends base service.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public interface SubjectService extends BaseService {

    /**
     * Find all subjects list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Subject> findAllSubjects() throws ServiceException;
}
