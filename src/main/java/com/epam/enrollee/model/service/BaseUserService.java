package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.UserServiceException;
import com.epam.enrollee.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface BaseUserService<T> {
    int createUser(User user) throws UserServiceException;

    int removeUser(User user) throws UserServiceException;

    Optional<T> findUserByEmail(String email) throws UserServiceException;

    Optional<T> findUserByRole(String Role) throws UserServiceException;

    Optional<T> findUserByStatus(String Status) throws UserServiceException;

    Optional<T> findUserById(int id) throws UserServiceException;

    List<T> findAll() throws UserServiceException;

    List<User> update(User user) throws UserServiceException;
}
