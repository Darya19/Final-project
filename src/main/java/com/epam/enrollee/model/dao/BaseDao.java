package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Base dao.
 * The interface defines base methods for working with all tables in the database.
 *
 * @param <T> the type parameter
 * @author Darya Shcherbina
 * @version 1.0
 */
public interface BaseDao<T> {

    /**
     * Add boolean.
     *
     * @param objectMap the object map
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean add(Map<String, Object> objectMap) throws DaoException;

    /**
     * Remove boolean.
     *
     * @param parameters the parameters
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean remove(Map<String, Object> parameters) throws DaoException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> findById(int id) throws DaoException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<T> findAll() throws DaoException;

    /**
     * Close statement.
     *
     * @param statement the statement
     * @throws DaoException the dao exception
     */
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