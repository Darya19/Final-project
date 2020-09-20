package com.epam.task1.model.dao.impl;

import com.epam.task1.exception.UserDaoException;
import com.epam.task1.model.connector.ConnectorDB;
import com.epam.task1.model.dao.BaseDao;
import com.epam.task1.model.dao.ColumnName;
import com.epam.task1.model.entity.User;
import com.epam.task1.model.enumtype.RoleType;
import com.epam.task1.model.enumtype.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements BaseDao {
    private static final String CREATE_USER = "INSERT INTO user(id, email, password, role) " +
            "VALUES (?,?,?,?)";
    private static final String REMOVE_USER = "DELETE FROM user WHERE email=?, password=?, role=?";
    private static final String GET_USER_BY_ID = "SELECT FROM user WHERE id=?";
    private static final String GET_USER_BY_EMAIL = "SELECT FROM user WHERE email=?";
    private static final String GET_USER_BY_PASSWORD = "SELECT FROM user WHERE password=?";
    private static final String GET_ALL_USERS = "SELECT id, email, password, role FROM user";
    private static final String GET_USERS_BY_ROLE = "SELECT FROM user WHERE role=? ORDER BY id";
    private static final String UPDATE_USER = "UPDATE user SET email=?, password=?, role=? where id=?";

    @Override
    public int createUser(User user) throws UserDaoException {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_USER)) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole().toString());
            int updatedRows = statement.executeUpdate();
            return updatedRows;
        } catch (SQLException e) {
            throw new UserDaoException("database issues", e);
        }
    }

    @Override
    public int removeUser(User user) throws UserDaoException {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_USER)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().toString());
            int updatedRows = statement.executeUpdate();
            return updatedRows;
        } catch (SQLException e) {
            throw new UserDaoException("database issues", e);
        }
    }
    @Override
    public Optional<List<User>> getUserById(int parameter) throws UserDaoException {
        try (Connection connection = ConnectorDB.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID);
            statement.setInt(1, parameter);
            ResultSet resultSet = statement.executeQuery();
            List<User> foundUsers = createUserListFromResultSet(resultSet);
            return Optional.of(foundUsers);
        } catch (SQLException e) {
            throw new UserDaoException("database issues");
        }
    }

    @Override
    public Optional<List<User>> getUser(Tag tag, String parameter) throws UserDaoException {
        try (Connection connection = ConnectorDB.getConnection()) {
            switch (tag) {
                case EMAIL:
                    PreparedStatement statement = connection.prepareStatement(GET_USER_BY_EMAIL);
                    statement.setString(1, parameter);
                    ResultSet resultSet = statement.executeQuery();
                    List<User> foundUsers = createUserListFromResultSet(resultSet);
                    return Optional.of(foundUsers);
                case PASSWORD:
                    statement = connection.prepareStatement(GET_USER_BY_PASSWORD);
                    statement.setString(1, parameter);
                    resultSet = statement.executeQuery();
                    foundUsers = createUserListFromResultSet(resultSet);
                    return Optional.of(foundUsers);
                case ROLE:
                    statement = connection.prepareStatement(GET_USERS_BY_ROLE);
                    statement.setString(1, parameter);
                    resultSet = statement.executeQuery();
                    foundUsers = createUserListFromResultSet(resultSet);
                    return Optional.of(foundUsers);
            }
        } catch (SQLException e) {
            throw new UserDaoException("database issues", e);
        }
        return Optional.empty();
    }

    @Override
    public List getAll() throws UserDaoException {
        PreparedStatement statement = null;
        try (Connection connection = ConnectorDB.getConnection()) {
            statement = connection.prepareStatement(GET_ALL_USERS);
            ResultSet resultSet = statement.executeQuery();
            List<User> foundBooks = createUserListFromResultSet(resultSet);
            return foundBooks;
        } catch (SQLException e) {
            throw new UserDaoException("database issues");//TODO unchecked exception?
        }
    }

    public List<User> update(User user) throws UserDaoException {
        try (Connection connection = ConnectorDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3,user.getRole().toString());
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
            user.setPassword(resultSet.getString(ColumnName.PASSWORD));
            user.setRole(RoleType.valueOf(resultSet.getString(ColumnName.ROLE)));
            users.add(user);
        }
        return users;
    }
}
