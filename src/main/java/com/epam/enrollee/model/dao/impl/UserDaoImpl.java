package com.epam.enrollee.model.dao.impl;

import com.epam.enrollee.exception.UserDaoException;
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
import java.util.Optional;

public class UserDaoImpl implements BaseDao {
    private static final String CREATE_USER = "INSERT INTO user(id, email, role, status) " +
            "VALUES (?,?,?,?)";
    private static final String REMOVE_USER = "DELETE FROM user WHERE email=?";
    private static final String FIND_USER_BY_ID =
            "SELECT id, email, role, status FROM user WHERE id=?";
    private static final String FIND_USER_BY_EMAIL =
            "SELECT id, email, role, status FROM user WHERE email=?";
    private static final String FIND_USERS_BY_STATUS =
            "SELECT id, email, role, status FROM user WHERE status=?";
    private static final String FIND_ALL_USERS = "SELECT id, email, role, status FROM user";
    private static final String FIND_USERS_BY_ROLE =
            "SELECT id, email, role, status FROM user WHERE role=? ORDER BY id";
    private static final String FIND_PASSWORD_BY_EMAIL = "SELECT password where email=?";
    private static final String UPDATE_USER = "UPDATE user SET email=?, password=?, role=? where id=?";

    @Override
    public int createUser(User user) throws UserDaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getRole().toString());
            statement.setString(4, user.getStatus().toString());
            int updatedRows = statement.executeUpdate();
            return updatedRows;
        } catch (SQLException e) {
            throw new UserDaoException("database issues", e);
        }
    }

    @Override
    public int removeUser(User user) throws UserDaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_USER)) {
            statement.setString(1, user.getEmail());
            int updatedRows = statement.executeUpdate();
            return updatedRows;
        } catch (SQLException e) {
            throw new UserDaoException("database issues", e);
        }
    }

    @Override
    public Optional<List<User>> findUserById(int id) throws UserDaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<User> foundUsers = createUserListFromResultSet(resultSet);
            return Optional.of(foundUsers);
        } catch (SQLException e) {
            throw new UserDaoException("database issues");
        }
    }

    @Override
    public Optional<List<User>> findUserByEmail(String email) throws UserDaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_USER_BY_EMAIL)){
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            List<User> foundUsers = createUserListFromResultSet(resultSet);
            return Optional.of(foundUsers);
        } catch (SQLException e) {
            throw new UserDaoException("database issues", e);
        }
    }

    @Override
    public Optional<List<User>> findUserByRole(RoleType role) throws UserDaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_USERS_BY_ROLE)){
            statement.setString(1, role.getRole());
            ResultSet resultSet = statement.executeQuery();
            List<User> foundUsers = createUserListFromResultSet(resultSet);
            return Optional.of(foundUsers);
        } catch (SQLException e) {
            throw new UserDaoException("database issues", e);
        }
    }

    @Override
    public Optional<List<User>> findUserByStatus(StatusType status) throws UserDaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_USERS_BY_STATUS)){
            statement.setString(1, status.getStatus());
            ResultSet resultSet = statement.executeQuery();
            List<User> foundUsers = createUserListFromResultSet(resultSet);
            return Optional.of(foundUsers);
        } catch (SQLException e) {
            throw new UserDaoException("database issues", e);
        }
    }

    public Optional<String> findPasswordByEmail(String email) throws UserDaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_PASSWORD_BY_EMAIL)){
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getString(ColumnName.PASSWORD));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new UserDaoException("database issues", e);
        }
    }

    @Override
    public Optional<List<User>> findAll() throws UserDaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)){
            ResultSet resultSet = statement.executeQuery();
            List<User> foundUsers = createUserListFromResultSet(resultSet);
            return Optional.of(foundUsers);
        } catch (SQLException e) {
            throw new UserDaoException("database issues");
        }
    }

    public List<User> update(User user) throws UserDaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)){
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getRole().getRole());
            statement.setString(3, user.getStatus().getStatus());
            statement.setInt(4, user.getId());
            ResultSet resultSet = statement.executeQuery();
            List<User> foundBooks = createUserListFromResultSet(resultSet);
            return foundBooks;
        } catch (SQLException e) {
            throw new UserDaoException("database issues", e);
        }
    }

    private List<User> createUserListFromResultSet(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt(ColumnName.ID));
            user.setEmail(resultSet.getString(ColumnName.EMAIL));
            user.setRole(RoleType.valueOf(resultSet.getString(ColumnName.ROLE)));
            user.setStatus(StatusType.valueOf(resultSet.getString(ColumnName.STATUS)));
            users.add(user);
        }
        return users;
    }
}
