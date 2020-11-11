package com.epam.enrollee.model.dao.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.connector.ConnectionPool;
import com.epam.enrollee.model.dao.BaseDao;
import com.epam.enrollee.model.dao.ColumnName;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.type.ApplicationStatus;
import com.epam.enrollee.model.type.RecruitmentType;
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

import static com.epam.enrollee.model.dao.ColumnName.FACULTY_ID;

public class SpecialtyDaoImpl implements BaseDao<Specialty> {

    public static SpecialtyDaoImpl instance;

    private static final String FIND_ALL_SPECIALTIES =
            "SELECT specialty_id, specialty_name, recruitment, number_of_seats, specialty_status" +
                    " FROM specialty WHERE recruitment=?";
    private static final String FIND_FACULTY_ID_BY_SPECIALTY_ID =
            "SELECT faculty_id_fk as faculty_id FROM  specialty WHERE specialty_id=?";
    private static final String FIND_SPECIALTY_BY_SPECIALTY_ID =
            "SELECT specialty_id, specialty_name, recruitment, number_of_seats, specialty_status" +
                    " FROM specialty WHERE specialty_id=?";
    private static final String FIND_OPEN_SPECIALTIES_BY_FACULTY_ID =
            "SELECT specialty_id, specialty_name, recruitment, number_of_seats, specialty_status" +
                    " FROM specialty WHERE recruitment=? and faculty_id_fk=?";
    private static final String FIND_SPECIALTY_BY_ENROLLEE_ID =
            "SELECT specialty_id, specialty_name, faculty_id_fk, recruitment, number_of_seats, " +
                    "specialty_status FROM specialty WHERE specialty_id IN (SELECT DISTINCT " +
                    "specialty_id_fk FROM enrollee_specialty WHERE enrollee_id_fk=?)";
    private static final String FIND_ALL_ACTIVE_SPECIALTIES_BY_FACULTY_ID =
            "SELECT specialty_id, specialty_name, recruitment, number_of_seats, specialty_status" +
                    " FROM specialty WHERE specialty_status=? and faculty_id_fk=?";
    private static final String UPDATE_RECRUITMENT_BY_SPECIALTY_ID = "UPDATE specialty SET recruitment=? WHERE specialty_id=?";
    private static final String FIND_ENROLLE_ID_BY_SPECIALTY_ID =
            "SELECT enrollee_id_fk as enrollee_id FROM enrollee_specialty JOIN enrollee WHERE application_status=? and " +
                    "specialty_id_fk=?";
    private static final String ADD_SPECIALTY =
            "INSERT INTO specialty(specialty_name, faculty_id_fk, recruitment, number_of_seats, " +
                    "specialty_status) VALUES (?,?,?,?,?)";
    private static final String UPDATE_SPECIALTY_STATUS_BY_ID = "UPDATE specialty SET specialty_status=?" +
            "WHERE specialty_id=?";
    private static final String UPDATE_SPECIALTY_BY_ID = "UPDATE specialty SET specialty_name=?, " +
            "number_of_seats=? WHERE specialty_id=?";

    public static SpecialtyDaoImpl getInstance() {
        if (instance == null) {
            instance = new SpecialtyDaoImpl();
        }
        return instance;
    }

    public boolean add(Map<String, Object> parameters) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_SPECIALTY)) {
            statement.setString(1,(String) parameters.get(MapKeys.SPECIALTY_NAME));
            statement.setInt(2, (int) parameters.get(MapKeys.FACULTY_ID));
            statement.setString(3,RecruitmentType.CLOSED.getType());
            statement.setInt(4, (int) parameters.get(MapKeys.NUMBER_OF_SEATS));
            statement.setString(5, StatusType.ACTIVE.getStatus());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public boolean remove(Map<String, Object> parameters) throws DaoException {
        return true;
    }

    public boolean update(Map<String, Object> parameters) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SPECIALTY_BY_ID)) {
            statement.setString(1, (String) parameters.get(MapKeys.SPECIALTY_NAME));
            statement.setInt(2, (int) parameters.get(MapKeys.NUMBER_OF_SEATS));
            statement.setInt(3, (int) parameters.get(MapKeys.SPECIALTY_ID));
            isUpdated = statement.executeUpdate() > 0;
            return isUpdated;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public Optional<Specialty> findById(int specialtyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_SPECIALTY_BY_SPECIALTY_ID)) {
            statement.setInt(1, specialtyId);
            ResultSet resultSet = statement.executeQuery();
            List<Specialty> specialties = createSpecialtyListFromResultSet(resultSet);
            if (!specialties.isEmpty()) {
                Specialty specialty = specialties.get(0);
                return Optional.of(specialty);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public List<Specialty> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SPECIALTIES)) {
            statement.setString(1, RecruitmentType.OPENED.getType());
            ResultSet resultSet = statement.executeQuery();
            List<Specialty> specialties = createSpecialtyListFromResultSet(resultSet);
            return specialties;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public Optional<Integer> findFacultyIdBySpecialtyId(int specialtyId) throws DaoException {
        int foundFacultyId = 0;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_FACULTY_ID_BY_SPECIALTY_ID)) {
            statement.setInt(1, specialtyId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foundFacultyId = resultSet.getInt(FACULTY_ID);
                return Optional.of(foundFacultyId);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public List<Specialty> findOpenSpecialtiesListByFacultyId(int facultyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_OPEN_SPECIALTIES_BY_FACULTY_ID)) {
            statement.setString(1, RecruitmentType.OPENED.getType());
            statement.setInt(2, facultyId);
            ResultSet resultSet = statement.executeQuery();
            List<Specialty> foundSpecialties = createSpecialtyListFromResultSet(resultSet);
            return foundSpecialties;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public List<Specialty> findActiveSpecialtiesListByFacultyId(int facultyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (FIND_ALL_ACTIVE_SPECIALTIES_BY_FACULTY_ID)) {
            statement.setString(1, StatusType.ACTIVE.getStatus());
            statement.setInt(2, facultyId);
            ResultSet resultSet = statement.executeQuery();
            List<Specialty> foundSpecialties = createSpecialtyListFromResultSet(resultSet);
            return foundSpecialties;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public Optional<Specialty> findSpecialtyByErolleeId(int enrolleeId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_SPECIALTY_BY_ENROLLEE_ID)) {
            statement.setInt(1, enrolleeId);
            ResultSet resultSet = statement.executeQuery();
            List<Specialty> foundSpecialties = createSpecialtyListFromResultSet(resultSet);
            if (!foundSpecialties.isEmpty()) {
                return Optional.of(foundSpecialties.get(0));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public boolean updateRecruitmentBySpecialtyId(int specialtyId, String recruitment) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (UPDATE_RECRUITMENT_BY_SPECIALTY_ID)) {
            statement.setString(1, recruitment);
            statement.setInt(2, specialtyId);
            isUpdated = statement.executeUpdate() > 0;
            return isUpdated;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public List<Integer> findConsideredEnrolleeIdBySpecialtyId(int specialtyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
                PreparedStatement statement = connection.prepareStatement(FIND_ENROLLE_ID_BY_SPECIALTY_ID)) {
            statement.setString(1, ApplicationStatus.CONSIDERED.getApplicationStatus());
            statement.setInt(2, specialtyId);
            ResultSet resultSet = statement.executeQuery();
            List<Integer> foundEnrolleeId = new ArrayList<>();
            while (resultSet.next()) {
                foundEnrolleeId.add(resultSet.getInt(ColumnName.ENROLLE_ID));
            }
            return foundEnrolleeId;
        } catch (
                SQLException e) {
            throw new DaoException("database issues", e);
        }

    }

    private List<Specialty> createSpecialtyListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Specialty> specialties = new ArrayList<>();
        while (resultSet.next()) {
            Specialty specialty = new Specialty();
            specialty.setSpecialtyId(resultSet.getInt(ColumnName.SPECIALTY_ID));
            specialty.setSpecialtyName(resultSet.getString(ColumnName.SPECIALTY_NAME));
            specialty.setRecruitment(RecruitmentType.valueOf
                    (resultSet.getString(ColumnName.RECRUITMENT).toUpperCase()));
            specialty.setNumberOfSeats(resultSet.getInt(ColumnName.NUMBER_OF_SEATS));
            specialty.setSpecialtyStatus(StatusType
                    .valueOf(resultSet.getString(ColumnName.SPECIALTY_STATUS).toUpperCase()));
            specialties.add(specialty);
        }
        return specialties;
    }

    public boolean updateSpecialtyStatusById(int specialtyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SPECIALTY_STATUS_BY_ID)) {
            statement.setString(1, StatusType.DELETED.getStatus());
            statement.setInt(2, specialtyId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }
}
