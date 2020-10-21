package com.epam.enrollee.model.dao.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.connector.ConnectionPool;
import com.epam.enrollee.model.dao.BaseDao;
import com.epam.enrollee.model.dao.ColumnName;
import com.epam.enrollee.model.entity.Faculty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FacultyDaoImpl implements BaseDao<Faculty> {

    private static final String FIND_FACULTY_BY_USER_ID =
            "SELECT faculty_id , faculty_name FROM faculty WHERE faculty_id IN (SELECT DISTINCT faculty_id_fk FROM user_faculty WHERE user_id_fk=?)";

    @Override
    public int add(Faculty faculty) throws DaoException {
        return 0;
    }

    @Override
    public int remove(Faculty faculty) throws DaoException {
        return 0;
    }

    @Override
    public Optional<List<Faculty>> findById(int parameter) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<List<Faculty>> findAll() throws DaoException {
        return Optional.empty();
    }

    public Optional<Faculty> findFacultyByUserId(int userId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_FACULTY_BY_USER_ID)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            Faculty foundFaculty = createFacultyListFromResultSet(resultSet).get(0);
            return Optional.of(foundFaculty);
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    private List<Faculty> createFacultyListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Faculty> faculties = new ArrayList<>();
        while (resultSet.next()) {
            Faculty faculty = new Faculty();
            faculty.setFacultyId(resultSet.getInt(ColumnName.FACULTY_ID));
            faculty.setFacultyName(resultSet.getString(ColumnName.FACULTY_NAME));
            faculties.add(faculty);
        }
        return faculties;
    }
}
