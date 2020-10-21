package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface BaseDao<T> {

    int add(T t) throws DaoException;

    int remove(T t) throws DaoException;

    Optional<List<T>> findById(int parameter) throws DaoException;

    Optional<List<T>> findAll() throws DaoException;

    //TODO sort methods

    default void closeStatement(Statement statement) throws DaoException {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }
}
