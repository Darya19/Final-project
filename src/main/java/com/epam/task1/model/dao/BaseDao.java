package com.epam.task1.model.dao;

import com.epam.task1.exception.UserDaoException;
import com.epam.task1.model.entity.User;
import com.epam.task1.model.enumtype.Tag;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    int createUser(User user) throws UserDaoException;

    int removeUser(User user) throws UserDaoException;

    Optional<T> getUser(Tag tag, String parameter) throws UserDaoException;

    Optional<T> getUserById(int parameter) throws UserDaoException;

    List<T> getAll() throws UserDaoException;

    default void closeStatement(Statement statement) throws UserDaoException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new UserDaoException("database issues", e);
        }
    }
}
