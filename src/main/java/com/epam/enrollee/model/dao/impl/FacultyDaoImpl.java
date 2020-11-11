package com.epam.enrollee.model.dao.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.connector.ConnectionPool;
import com.epam.enrollee.model.dao.BaseDao;
import com.epam.enrollee.model.dao.ColumnName;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.type.StatusType;
import com.epam.enrollee.util.MapKeys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FacultyDaoImpl implements BaseDao<Faculty> {

    public static FacultyDaoImpl instance;

    private static final String FIND_FACULTY_BY_ENROLLEE_ID =
            "SELECT faculty_id , faculty_name, faculty_description, faculty_status FROM faculty " +
                    "WHERE faculty_id IN " +
                    "(SELECT DISTINCT faculty_id_fk FROM enrollee_faculty WHERE enrollee_id_fk=?)";
    private static final String FIND_FACULTY_BY_FACULTY_ID =
            "SELECT faculty_id, faculty_name, faculty_description, faculty_status FROM faculty " +
                    "WHERE faculty_id=?";
    private static final String FIND_ALL_ACTIVE_FACULTIES =
            "SELECT faculty_id, faculty_name, faculty_description, faculty_status FROM faculty " +
                    "WHERE faculty_status=?";
    private static final String FIND_ENROLLE_ID_BY_FACULTY_ID = "SELECT enrollee_id_fk as enrollee_id " +
            "FROM enrollee_faculty WHERE faculty_id_fk=?";
    private static final String UPDATE_FACULTY_STATUS_BY_ID = "UPDATE faculty SET faculty_status=?" +
            "WHERE faculty_id=?";
    private static final String UPDATE_FACULTY_BY_ID = "UPDATE faculty SET faculty_name=?, " +
            "faculty_description=? WHERE faculty_id=?";
    private static final String ADD_FACULTY =
            "INSERT INTO faculty(faculty_name,faculty_description,faculty_status) VALUES (?,?,?)";

    public static FacultyDaoImpl getInstance() {
        if (instance == null) {
            instance = new FacultyDaoImpl();
        }
        return instance;
    }

    public boolean add(Map<String, Object> parameters) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_FACULTY)) {
            statement.setString(1, (String) parameters.get(MapKeys.FACULTY_NAME));
            statement.setString(2, (String) parameters.get(MapKeys.FACULTY_DESCRIPTION));
            statement.setString(3, StatusType.ACTIVE.getStatus());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public boolean remove(Map<String, Object> parameters) throws DaoException {
        return true;
    }

    public boolean updateStatusById(int facultyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_FACULTY_STATUS_BY_ID)) {
            statement.setString(1, StatusType.DELETED.getStatus());
            statement.setInt(2, facultyId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public boolean update(Map<String, Object> parameters) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_FACULTY_BY_ID)) {
            statement.setString(1, (String) parameters.get(MapKeys.FACULTY_NAME));
            statement.setString(2, (String) parameters.get(MapKeys.FACULTY_NAME));
            statement.setInt(3, (int) parameters.get(MapKeys.FACULTY_ID));
            isUpdated = statement.executeUpdate() > 0;
            return isUpdated;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public Optional<Faculty> findById(int facultyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_FACULTY_BY_FACULTY_ID)) {
            statement.setInt(1, facultyId);
            ResultSet resultSet = statement.executeQuery();
            List<Faculty> faculties = createFacultyListFromResultSet(resultSet);
            if (!faculties.isEmpty()) {
                Faculty faculty = faculties.get(0);
                return Optional.of(faculty);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public List<Faculty> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ACTIVE_FACULTIES)) {
            statement.setString(1, StatusType.ACTIVE.getStatus());
            ResultSet resultSet = statement.executeQuery();
            List<Faculty> faculties = createFacultyListFromResultSet(resultSet);
            return faculties;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public Optional<Faculty> findFacultyByEnrolleeId(int enrolleeId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_FACULTY_BY_ENROLLEE_ID)) {
            statement.setInt(1, enrolleeId);
            ResultSet resultSet = statement.executeQuery();
            List<Faculty> foundFaculties = createFacultyListFromResultSet(resultSet);
            if (!foundFaculties.isEmpty()) {
                return Optional.of(foundFaculties.get(0));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public List<Integer> findEnrolleeIdByFacultyId(int facultyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ENROLLE_ID_BY_FACULTY_ID)) {
            statement.setInt(1, facultyId);
            ResultSet resultSet = statement.executeQuery();
            List<Integer> foundEnrolleeId = new ArrayList<>();
            while (resultSet.next()) {
                foundEnrolleeId.add(resultSet.getInt(ColumnName.ENROLLE_ID));
            }
            return foundEnrolleeId;
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
            faculty.setFacultyDescription(resultSet.getString(ColumnName.FACULTY_DESCRIPTION));
            faculty.setFacultyStatus(StatusType.valueOf(resultSet.getString(ColumnName.FACULTY_STATUS).toUpperCase()));
            faculties.add(faculty);
        }
        return faculties;
    }
}
