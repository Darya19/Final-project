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
import java.util.*;

import static com.epam.enrollee.model.dao.ColumnName.*;

public class FacultyDaoImpl implements BaseDao<Faculty> {

    private static final String FIND_FACULTY_BY_USER_ID =
            "SELECT faculty_id , faculty_name FROM faculty WHERE faculty_id IN (SELECT DISTINCT faculty_id_fk FROM enrollee_faculty WHERE enrollee_id_fk=?)";
    private static final String FIND_FACULTY_BY_FACULTY_ID =
            "SELECT faculty_id, faculty_name, GROUP_CONCAT(specialty_id ORDER BY specialty_id ASC separator ',')" +
                    "AS specialty_id FROM faculty LEFT JOIN specialty ON specialty.faculty_id_fk = faculty.faculty_id " +
                    "WHERE faculty_id=? GROUP BY faculty_id";
    private static final String FIND_ALL_FACULTIES =
            "SELECT faculty_id, faculty_name, GROUP_CONCAT(specialty_id ORDER BY specialty_id ASC separator ',')" +
                    "AS specialty_id FROM faculty LEFT JOIN specialty ON specialty.faculty_id_fk = faculty.faculty_id " +
                    "GROUP BY faculty_id";

    @Override
    public boolean add (Map<String, Object> parameters) throws DaoException {
        return true;
    }

    @Override
    public boolean remove(Faculty faculty) throws DaoException {
        return true;
    }

    @Override
    public Optional<List<Faculty>> findById(int facultyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_FACULTY_BY_FACULTY_ID)) {
            statement.setInt(1, facultyId);
            ResultSet resultSet = statement.executeQuery();
            List<Faculty> faculties = createFacultyListFromResultSet(resultSet);
            if (!faculties.isEmpty()) {
                return Optional.of(faculties);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }
//check
    @Override
    public Optional<List<Faculty>> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_FACULTIES)) {
            ResultSet resultSet = statement.executeQuery();
            List<Faculty> faculties = createFacultyListFromResultSet(resultSet);
            if (!faculties.isEmpty()) {
                return Optional.of(faculties);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public Optional<Faculty> findFacultyByUserId(int userId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_FACULTY_BY_USER_ID)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<Faculty> foundFaculties = createFacultyListFromResultSet(resultSet);
            if (foundFaculties.size() > 0) {
                return Optional.of(foundFaculties.get(0));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    private List<Faculty> createFacultyListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Faculty> faculties = new ArrayList<>();
        while (resultSet.next()) {
            Faculty faculty = new Faculty();
            faculty.setFacultyId(resultSet.getInt(FACULTY_ID));
            faculty.setFacultyName(resultSet.getString(FACULTY_NAME));
            String[] specialties = resultSet.getString(SPECIALTY_ID).split(",");
            List<String> specialtiesList = Arrays.asList(specialties);
            for (String id : specialtiesList) {
                faculty.add(Integer.valueOf(id));
            }
            faculties.add(faculty);
        }
        return faculties;
    }
}
