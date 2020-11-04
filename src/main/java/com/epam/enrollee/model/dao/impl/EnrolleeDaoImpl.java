package com.epam.enrollee.model.dao.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.connector.ConnectionPool;
import com.epam.enrollee.model.dao.BaseDao;
import com.epam.enrollee.model.dao.ColumnName;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Passport;
import com.epam.enrollee.model.type.ApplicationStatus;
import com.epam.enrollee.model.type.RoleType;
import com.epam.enrollee.model.type.StatusType;
import com.epam.enrollee.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EnrolleeDaoImpl implements BaseDao<Enrollee> {

    public static EnrolleeDaoImpl instance;

    private static Logger logger = LogManager.getLogger();
    private static final String FIND_ENROLLEE_BY_EMAIL =
            "SELECT enrollee_id,email,role,status,enrollee_first_name,enrollee_last_name," +
                    "enrollee_middle_name, faculty_id_fk as faculty_id,specialty_id_fk as " +
                    "specialty_id FROM enrollee JOIN enrollee_faculty on enrollee.enrollee_id " +
                    "= enrollee_faculty.enrollee_id_fk JOIN enrollee_specialty on enrollee.enrollee_id " +
                    "= enrollee_specialty.enrollee_id_fk WHERE email=?";
    private static final String ADD_ENROLLEE =
            "INSERT INTO enrollee (email, password, status, role, enrollee_first_name, " +
                    "enrollee_last_name, enrollee_middle_name, passport_id_fk) " +
                    "VALUES (?,?,?,?,?,?,?,?)";
    private static final String ADD_PASSPORT =
            "INSERT INTO passport (personal_number, passport_series_and_number) VALUES (?,?)";
    private static final String ADD_MARKS =
            "INSERT INTO mark (enrollee_id_fk, subject_id_fk, mark_value) " +
                    "VALUES (?,?,?)";
    private static final String ADD_ENROLLEE_IN_SPECIALTY =
            "INSERT INTO enrollee_specialty (enrollee_id_fk, specialty_id_fk) " +
                    "VALUES (?,?)";
    private static final String ADD_ENROLLEE_IN_FACULTY =
            "INSERT INTO enrollee_faculty (enrollee_id_fk,faculty_id_fk) " +
                    "VALUES (?,?)";
    private static final String FIND_PASSPORT_BY_ENROLLE_ID =
            "SELECT passport_id, personal_number, passport_series_and_number FROM passport " +
                    "JOIN enrollee e on passport.passport_id = e.passport_id_fk WHERE enrollee_id=?";
    private static final String UPDATE_ENROLLEE =
            "UPDATE enrollee SET enrollee_first_name=?, enrollee_last_name=?, enrollee_last_name=? where enrollee_id=?";
    private static final String UPDATE_PASSPORT =
            "UPDATE passport SET passport_series_and_number=?, personal_number=? where passport_id=?";
    private static final String UPDATE_SPECIALTY =
            "UPDATE enrollee_specialty SET specialty_id_fk=? where enrollee_id_fk=?";

    public static EnrolleeDaoImpl getInstance() {
        if (instance == null) {
            instance = new EnrolleeDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Map<String, Object> objectMap) throws DaoException {
        boolean isEnrolleeAdded = false;
        boolean isPassportAdded;
        int passportId = 0;
        int enrolleeId = 0;
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        PreparedStatement enrolleeStatement = null;
        PreparedStatement passportStatement = null;
        PreparedStatement markStatement = null;
        PreparedStatement specialtyStatement = null;
        PreparedStatement facultyStatement = null;
        try {
            connection.setAutoCommit(false);
            enrolleeStatement = connection.prepareStatement(ADD_ENROLLEE, Statement.RETURN_GENERATED_KEYS);
            passportStatement = connection.prepareStatement(ADD_PASSPORT, Statement.RETURN_GENERATED_KEYS);
            markStatement = connection.prepareStatement(ADD_MARKS);
            specialtyStatement = connection.prepareStatement(ADD_ENROLLEE_IN_SPECIALTY);
            facultyStatement = connection.prepareStatement(ADD_ENROLLEE_IN_FACULTY);
            passportStatement.setString(1, (String) objectMap.get(MapKeys.PERSONAL_NUMBER));
            passportStatement.setString(2, (String) objectMap.get(MapKeys.PASSPORT_SERIES_AND_NUMBER));
            isPassportAdded = passportStatement.executeUpdate() > 0;
            ResultSet passportResultSet = passportStatement.getGeneratedKeys();
            if (passportResultSet.next()) {
                passportId = passportResultSet.getInt(1);
            }
            if (isPassportAdded) {
                enrolleeStatement.setString(1, (String) objectMap.get(MapKeys.EMAIL));
                enrolleeStatement.setString(2, (String) objectMap.get(MapKeys.PASSWORD));
                enrolleeStatement.setString(3, String.valueOf(objectMap.get(MapKeys.STATUS)));
                enrolleeStatement.setString(4, String.valueOf(objectMap.get(MapKeys.ROLE)));
                enrolleeStatement.setString(5, (String) objectMap.get(MapKeys.FIRST_NAME));
                enrolleeStatement.setString(6, (String) objectMap.get(MapKeys.LAST_NAME));
                enrolleeStatement.setString(7, (String) objectMap.get(MapKeys.MIDDLE_NAME));
                enrolleeStatement.setInt(8, passportId);
                isEnrolleeAdded = enrolleeStatement.executeUpdate() > 0;
                ResultSet enrolleeResultSet = enrolleeStatement.getGeneratedKeys();
                if (enrolleeResultSet.next()) {
                    enrolleeId = enrolleeResultSet.getInt(1);
                }
                if (isEnrolleeAdded) {
                    facultyStatement.setInt(1, enrolleeId);
                    facultyStatement.setInt(2, (Integer) objectMap.get(MapKeys.FACULTY_ID));
                    facultyStatement.executeUpdate();
                    specialtyStatement.setInt(1, enrolleeId);
                    specialtyStatement.setInt(2, (Integer) objectMap.get(MapKeys.SPECIALTY_ID));
                    specialtyStatement.executeUpdate();
                    markStatement.setInt(1, enrolleeId);
                    markStatement.setInt(2, (Integer) objectMap.get(MapKeys.SUBJECT_ID_1));
                    markStatement.setInt(3, (Integer) objectMap.get(MapKeys.MARK_1));
                    markStatement.executeUpdate();
                    markStatement.setInt(1, enrolleeId);
                    markStatement.setInt(2, (Integer) objectMap.get(MapKeys.SUBJECT_ID_2));
                    markStatement.setInt(3, (Integer) objectMap.get(MapKeys.MARK_2));
                    markStatement.executeUpdate();
                    markStatement.setInt(1, enrolleeId);
                    markStatement.setInt(2, (Integer) objectMap.get(MapKeys.SUBJECT_ID_3));
                    markStatement.setInt(3, (Integer) objectMap.get(MapKeys.MARK_3));
                    markStatement.executeUpdate();
                    markStatement.setInt(1, enrolleeId);
                    markStatement.setInt(2, (Integer) objectMap.get(MapKeys.SUBJECT_ID_4));
                    markStatement.setInt(3, (Integer) objectMap.get(MapKeys.MARK_4));
                    markStatement.executeUpdate();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.log(Level.ERROR, "impossible rollback committing", ex);
            }
            throw new DaoException("impossible add new enrollee", e);
        } finally {
            closeStatement(enrolleeStatement);
            closeStatement(passportStatement);
            closeStatement(facultyStatement);
            closeStatement(specialtyStatement);
            closeStatement(markStatement);
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException throwables) {
                logger.log(Level.ERROR, "impossible return connection to pool");
            }
        }
        return isEnrolleeAdded;
    }

    @Override
    public boolean remove(Enrollee enrollee) throws DaoException {
        return true;
    }


    public boolean updateEnrollee(Enrollee enrollee) throws DaoException {
boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ENROLLEE)) {
            statement.setString(1, enrollee.getFirstName());
            statement.setString(2, enrollee.getLastName());
            statement.setString(3, enrollee.getMiddleName());
            statement.setInt(4, enrollee.getUserId());
            isUpdated = statement.executeUpdate() > 0;
            return isUpdated;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public boolean updatePassport(Passport passport) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_PASSPORT)) {
            statement.setString(1, passport.getPassportSeriesAndNumber());
            statement.setString(2, passport.getPersonalNumber());
            statement.setInt(3, passport.getPassportId());
            isUpdated = statement.executeUpdate() > 0;
            return isUpdated;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public boolean updateEnrolleeSpecialty(Enrollee enrollee) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SPECIALTY)) {
            statement.setInt(1, enrollee.getChosenSpecialtyId());
            statement.setInt(2, enrollee.getUserId());
            isUpdated = statement.executeUpdate() > 0;
            return isUpdated;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    @Override
    public Optional<Enrollee> findById(int parameter) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Enrollee> findAll() throws DaoException {
        return null;
    }

    public Optional<Enrollee> findEnrolleeByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ENROLLEE_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            List<Enrollee> enrollees = createEnrolleeListFromResultSet(resultSet);
            if (!enrollees.isEmpty()) {
                Enrollee enrollee = enrollees.get(0);
                return Optional.of(enrollee);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    public Optional<Passport> findPassportByEnrolleeId(int enrolleeId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_PASSPORT_BY_ENROLLE_ID)) {
            statement.setInt(1, enrolleeId);
            ResultSet resultSet = statement.executeQuery();
           Passport passport = createPassportFromResultSet(resultSet);
            if (passport != null) {
                return Optional.of(passport);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    private List<Enrollee> createEnrolleeListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Enrollee> enrollees = new ArrayList<>();
        while (resultSet.next()) {
            Enrollee enrollee = new Enrollee();
            enrollee.setUserId(resultSet.getInt(ColumnName.ENROLLE_ID));
            enrollee.setEmail(resultSet.getString(ColumnName.EMAIL));
            enrollee.setRole(RoleType.valueOf(resultSet.getString(ColumnName.ROLE).toUpperCase()));
            enrollee.setStatus(StatusType.valueOf(resultSet.getString(ColumnName.STATUS).toUpperCase()));
            enrollee.setApplicationStatus(ApplicationStatus
                    .valueOf(resultSet.getString(ColumnName.APPLICATION_STATUS).toUpperCase()));
            enrollee.setFirstName(resultSet.getString(ColumnName.ENROLLEE_FIRST_NAME));
            enrollee.setLastName(resultSet.getString(ColumnName.ENROLLEE_LAST_NAME));
            enrollee.setMiddleName(resultSet.getString(ColumnName.ENROLLEE_MIDDLE_NAME));
            enrollee.setChosenFacultyId(resultSet.getInt(ColumnName.FACULTY_ID));
            enrollee.setChosenSpecialtyId(resultSet.getInt(ColumnName.SPECIALTY_ID));
            enrollees.add(enrollee);
        }
        return enrollees;
    }

    private Passport createPassportFromResultSet(ResultSet resultSet) throws SQLException {
        Passport passport = new Passport();
        while (resultSet.next()){
            passport.setPassportId(resultSet.getInt(ColumnName.PASSPORT_ID));
            passport.setPersonalNumber(resultSet.getString(ColumnName.PERSONAL_NUMBER));
            passport.setPassportSeriesAndNumber(resultSet.getString(ColumnName.PASSPORT_SERIES_AND_NUMBER));
        }
        return passport;
    }
}
