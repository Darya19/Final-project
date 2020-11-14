package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;

import java.util.Map;

public interface BaseService {

    String EMPTY_VALUE = "";

    boolean create(Map<String, String> parameters) throws ServiceException;

    Map<String, String> checkParameters(Map<String, String> parameters) throws ServiceException;
}
