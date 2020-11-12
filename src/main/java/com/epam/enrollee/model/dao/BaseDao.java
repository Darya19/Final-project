package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.entity.Enrollee;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseDao<T> {

    boolean add(Map<String, Object> objectMap) throws DaoException;

    boolean remove(Map<String, Object> parameters) throws DaoException;

    Optional<T> findById(int id) throws DaoException;

    List<T> findAll() throws DaoException;

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
