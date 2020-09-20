package com.epam.task1.model.service;

import com.epam.task1.exception.UserDaoException;
import com.epam.task1.exception.UserServiceException;
import com.epam.task1.model.entity.User;
import com.epam.task1.model.enumtype.Tag;

import java.util.List;
import java.util.Optional;

public interface BaseUserService<T> {
    int createUser(User user) throws UserServiceException;

    int removeUser(User user) throws UserServiceException;

    Optional<T> getUser(Tag tag, String parameter) throws UserServiceException;

    Optional<T> getUserById(Tag tag, int parameter) throws UserServiceException;

    List<T> getAll() throws UserServiceException;
}
