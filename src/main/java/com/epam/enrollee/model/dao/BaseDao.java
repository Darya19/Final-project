package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.UserDaoException;
import com.epam.enrollee.model.entity.User;
import com.epam.enrollee.model.enumtype.RoleType;
import com.epam.enrollee.model.enumtype.StatusType;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    int createUser(User user) throws UserDaoException;

    int removeUser(User user) throws UserDaoException;

    Optional<T> findUserById(int parameter) throws UserDaoException;

    Optional<T> findUserByEmail(String email) throws UserDaoException;

    Optional<T> findUserByRole(RoleType Role) throws UserDaoException;

    Optional<T> findUserByStatus(StatusType status) throws UserDaoException;

    Optional<List<User>> findAll() throws UserDaoException;//TODO sort methods

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
