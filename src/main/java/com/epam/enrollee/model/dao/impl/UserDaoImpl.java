package com.epam.enrollee.model.dao.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.connector.ConnectionPool;
import com.epam.enrollee.model.dao.BaseDao;
import com.epam.enrollee.model.dao.ColumnName;
import com.epam.enrollee.model.dao.UserDao;
import com.epam.enrollee.model.entity.User;
import com.epam.enrollee.model.type.RoleType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type User dao.
 */
public class UserDaoImpl implements UserDao {

    /**
     * The constant instance.
     */
    public static UserDaoImpl instance;
    private static Logger logger = LogManager.getLogger();
    private static final String FIND_USER_BY_ID = "SELECT enrollee_id, email, role, status FROM enrollee WHERE id=?";
    private static final String FIND_USER_BY_EMAIL = "SELECT enrollee_id, email, role FROM enrollee WHERE email=?";
    private static final String FIND_ALL_USERS = "SELECT enrollee_id, email, role FROM user";
    private static final String FIND_PASSWORD_BY_EMAIL = "SELECT password from enrollee WHERE email=?";

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserDaoImpl getInstance() {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Map<String, Object> parameters) throws DaoException {
        throw new UnsupportedOperationException("Impossible add admin");
    }

    @Override
    public boolean remove(Map<String, Object> parameters) throws DaoException {
        throw new UnsupportedOperationException("Admin can't be removed");
    }

    @Override
    public Optional<User> findById(int id) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<User> foundUsers = createUserListFromResultSet(resultSet);
            if (!foundUsers.isEmpty()) {
                User user = foundUsers.get(0);
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible find user by id", e);
            throw new DaoException("Database issues while finding user by id", e);
        }
    }

    @Override
    public List<User> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            List<User> foundUsers = createUserListFromResultSet(resultSet);
            return foundUsers;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible find all users", e);
            throw new DaoException("Database issues while finding all users", e);
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            List<User> foundUsers = createUserListFromResultSet(resultSet);
            if (!foundUsers.isEmpty()) {
                return Optional.of(foundUsers.get(0));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible find user by email", e);
            throw new DaoException("Database issues while finding user by email", e);
        }
    }

    @Override
    public Optional<String> findPasswordByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PASSWORD_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getString(ColumnName.PASSWORD));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible find password by email", e);
            throw new DaoException("Database issues while finding password by email", e);
        }
    }

    private List<User> createUserListFromResultSet(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setUserId(resultSet.getInt(ColumnName.ENROLLEE_ID));
            user.setEmail(resultSet.getString(ColumnName.EMAIL));
            user.setRole(RoleType.valueOf(resultSet.getString(ColumnName.ROLE).toUpperCase()));
            users.add(user);
        }
        return users;
    }
}
