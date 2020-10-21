package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    int createUser(Enrollee user) throws ServiceException;

    int removeUser(Enrollee user) throws ServiceException;

    Optional<T> findUserByEmail(String email) throws ServiceException;

    Optional<T> findUserByRole(String Role) throws ServiceException;

    Optional<T> findUserByStatus(String Status) throws ServiceException;

    Optional<T> findUserById(int id) throws ServiceException;

    List<T> findAll() throws ServiceException;

    List<Enrollee> update(Enrollee user) throws ServiceException;
}
