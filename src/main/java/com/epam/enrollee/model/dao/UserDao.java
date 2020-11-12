package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao<User> {

    Optional<User> findUserByEmail(String email) throws DaoException;

    Optional<String> findPasswordByEmail(String email) throws DaoException;
}
