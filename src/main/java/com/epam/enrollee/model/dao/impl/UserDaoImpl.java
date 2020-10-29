package com.epam.enrollee.model.dao.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.connector.ConnectionPool;
import com.epam.enrollee.model.dao.BaseDao;
import com.epam.enrollee.model.dao.ColumnName;
import com.epam.enrollee.model.entity.User;
import com.epam.enrollee.model.enumtype.RoleType;
import com.epam.enrollee.model.enumtype.StatusType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserDaoImpl implements BaseDao<User> {
    private static final String CREATE_USER = "INSERT INTO user (user_id, email, role, status) " +
            "VALUES (?,?,?,?)";
    private static final String REMOVE_USER = "DELETE FROM user WHERE email=?";
    private static final String FIND_USER_BY_ID =
            "SELECT user_id, email, role, status FROM user WHERE id=?";
    private static final String FIND_USER_BY_EMAIL =
            "SELECT user_id, email, role, status FROM user WHERE email=?";
    private static final String FIND_USERS_BY_STATUS =
            "SELECT user_id, email, role, status FROM user WHERE status=?";
    private static final String FIND_ALL_USERS = "SELECT user_id, email, role, status FROM user";
    private static final String FIND_USERS_BY_ROLE =
            "SELECT user_id, email, role, status FROM user WHERE role=? ORDER BY id";
    private static final String FIND_PASSWORD_BY_EMAIL = "SELECT password from user where email=?";
    private static final String UPDATE_USER = "UPDATE account SET email=?, password=?, role=? where user_id=?";


    @Override
    public boolean add(Map<String, Object> parameters) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {
//            statement.setInt(1, user.getUserId());
//            statement.setString(2, user.getEmail());
//            statement.setString(3, user.getRole().toString());
//            statement.setString(4, user.getStatus().toString());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    @Override
    public boolean remove(User user) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_USER)) {
            statement.setString(1, user.getEmail());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    @Override
    public Optional<List<User>> findById(int id) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<User> foundUsers = createUserListFromResultSet(resultSet);
            return Optional.of(foundUsers);
        } catch (SQLException e) {
            throw new DaoException("database issues");
        }
    }

    public Optional<User> findUserByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            List<User> foundUsers = createUserListFromResultSet(resultSet);
            if (foundUsers.size() > 0) {
                return Optional.of(foundUsers.get(0));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public Optional<List<User>> findUsersByRole(RoleType role) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USERS_BY_ROLE)) {
            statement.setString(1, role.getRole());
            ResultSet resultSet = statement.executeQuery();
            List<User> foundUsers = createUserListFromResultSet(resultSet);
            return Optional.of(foundUsers);
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public Optional<List<User>> findUsersByStatus(StatusType status) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_USERS_BY_STATUS)) {
            statement.setString(1, status.getStatus());
            ResultSet resultSet = statement.executeQuery();
            List<User> foundUsers = createUserListFromResultSet(resultSet);
            return Optional.of(foundUsers);
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

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
            throw new DaoException("database issues", e);
        }
    }

    @Override
    public Optional<List<User>> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)) {
            ResultSet resultSet = statement.executeQuery();
            List<User> foundUsers = createUserListFromResultSet(resultSet);
            return Optional.of(foundUsers);
        } catch (SQLException e) {
            throw new DaoException("database issues");
        }
    }

    public List<User> update(User user) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getRole().getRole());
            statement.setString(3, user.getStatus().getStatus());
            statement.setInt(4, user.getUserId());
            ResultSet resultSet = statement.executeQuery();
            List<User> foundBooks = createUserListFromResultSet(resultSet);
            return foundBooks;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    private List<User> createUserListFromResultSet(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setUserId(resultSet.getInt(ColumnName.USER_ID));
            user.setEmail(resultSet.getString(ColumnName.EMAIL));
            user.setRole(RoleType.valueOf(resultSet.getString(ColumnName.ROLE).toUpperCase()));
            user.setStatus(StatusType.valueOf(resultSet.getString(ColumnName.STATUS).toUpperCase()));
            users.add(user);
        }
        return users;
    }
}
