package com.epam.enrollee.model.dao.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.connector.ConnectionPool;
import com.epam.enrollee.model.dao.BaseDao;
import com.epam.enrollee.model.dao.ColumnName;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.enumtype.RoleType;
import com.epam.enrollee.model.enumtype.StatusType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnrolleeDaoImpl implements BaseDao<Enrollee> {

    private static final String FIND_ENROLLEE_BY_EMAIL =
            "SELECT user_id, email, role, status, enrollee_name, enrollee_last_name, enrolle_surname FROM enrollee JOIN user on enrollee.enrollee_id = user.user_id WHERE email=?";

    @Override
    public int add(Enrollee enrollee) throws DaoException {
        return 0;
    }

    @Override
    public int remove(Enrollee enrollee) throws DaoException {
        return 0;
    }

    @Override
    public Optional<List<Enrollee>> findById(int parameter) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<List<Enrollee>> findAll() throws DaoException {
        return Optional.empty();
    }

    public Optional<Enrollee> findEnrolleeByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ENROLLEE_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            Enrollee foundEnrollee = createEnrolleeListFromResultSet(resultSet).get(0);
            return Optional.of(foundEnrollee);
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    private List<Enrollee> createEnrolleeListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Enrollee> enrollees = new ArrayList<>();
        while (resultSet.next()) {
            Enrollee enrollee = new Enrollee();
            enrollee.setUserId(resultSet.getInt(ColumnName.USER_ID));
            enrollee.setEmail(resultSet.getString(ColumnName.EMAIL));
            enrollee.setRole(RoleType.valueOf(resultSet.getString(ColumnName.ROLE).toUpperCase()));
            enrollee.setStatus(StatusType.valueOf(resultSet.getString(ColumnName.STATUS).toUpperCase()));
            enrollee.setUserName(resultSet.getString(ColumnName.USER_NAME));
            enrollee.setLastName(resultSet.getString(ColumnName.USER_LAST_NAME));
           // enrollee.setIdentificationNumber(resultSet.getString(ColumnName.IDENTIFICATION_NUMBER));
            enrollees.add(enrollee);
        }
        return enrollees;
    }
}
