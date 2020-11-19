package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;

import java.util.Map;

/**
 * The interface Base service.
 * The interface defines base methods for all objects in the system.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public interface BaseService {

    /**
     * The constant EMPTY_VALUE.
     */
    String EMPTY_VALUE = "";

    /**
     * Create boolean.
     * Create object.
     *
     * @param parameters the parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean create(Map<String, String> parameters) throws ServiceException;

    /**
     * Check parameters map.
     * Check input parameters for updating objects.
     *
     * @param parameters the parameters
     * @return the map
     * @throws ServiceException the service exception
     */
    Map<String, String> checkParameters(Map<String, String> parameters) throws ServiceException;
}