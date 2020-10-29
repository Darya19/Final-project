package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Faculty;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseService<T> {
    Optional<T> create(Map<String, String> parameters) throws ServiceException;

    int remove(T value) throws ServiceException;

    Optional<T> find(String value) throws ServiceException;

    Optional<List<T>> findAll() throws ServiceException;

    List<T> update(T value) throws ServiceException;
}
