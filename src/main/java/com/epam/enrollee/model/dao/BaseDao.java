package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseDao<T> {

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
