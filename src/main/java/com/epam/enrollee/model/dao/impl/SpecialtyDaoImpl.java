package com.epam.enrollee.model.dao.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.connector.ConnectionPool;
import com.epam.enrollee.model.dao.ColumnName;
import com.epam.enrollee.model.dao.SpecialtyDao;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.type.ApplicationStatus;
import com.epam.enrollee.model.type.RecruitmentType;
import com.epam.enrollee.model.type.StatusType;
import com.epam.enrollee.util.MapKeys;
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

import static com.epam.enrollee.model.dao.ColumnName.FACULTY_ID;

/**
 * The type Specialty dao.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class SpecialtyDaoImpl implements SpecialtyDao {

    /**
     * The constant instance.
     */
    public static SpecialtyDaoImpl instance;
    private static Logger logger = LogManager.getLogger();
    private static final String FIND_ALL_SPECIALTIES = "SELECT specialty_id, specialty_name, recruitment, number_of_seats," +
            " specialty_status FROM specialty WHERE recruitment=?";
    private static final String FIND_FACULTY_ID_BY_SPECIALTY_ID = "SELECT faculty_id_fk as faculty_id FROM  specialty " +
            "WHERE specialty_id=?";
    private static final String FIND_SPECIALTY_BY_SPECIALTY_ID = "SELECT specialty_id, specialty_name, recruitment, " +
            "number_of_seats, specialty_status FROM specialty WHERE specialty_id=?";
    private static final String FIND_OPEN_SPECIALTIES_BY_FACULTY_ID = "SELECT specialty_id, specialty_name, recruitment," +
            " number_of_seats, specialty_status FROM specialty WHERE recruitment=? and faculty_id_fk=?";
    private static final String FIND_SPECIALTY_BY_ENROLLEE_ID = "SELECT specialty_id, specialty_name, faculty_id_fk, " +
            "recruitment, number_of_seats, specialty_status FROM specialty WHERE specialty_id IN (SELECT DISTINCT " +
            "specialty_id_fk FROM enrollee_specialty WHERE enrollee_id_fk=?)";
    private static final String FIND_ALL_ACTIVE_SPECIALTIES_BY_FACULTY_ID = "SELECT specialty_id, specialty_name, " +
            "recruitment, number_of_seats, specialty_status FROM specialty WHERE specialty_status=? and faculty_id_fk=?";
    private static final String UPDATE_RECRUITMENT_BY_SPECIALTY_ID = "UPDATE specialty SET recruitment=? WHERE specialty_id=?";
    private static final String FIND_ENROLLE_ID_BY_SPECIALTY_ID = "SELECT enrollee_id_fk as enrollee_id FROM " +
            "enrollee JOIN enrollee_specialty es on enrollee.enrollee_id = es.enrollee_id_fk  WHERE application_status=? " +
            "and specialty_id_fk=?";
    private static final String ADD_SPECIALTY = "INSERT INTO specialty(specialty_name, faculty_id_fk, recruitment, " +
            "number_of_seats, specialty_status) VALUES (?,?,?,?,?)";
    private static final String UPDATE_SPECIALTY_STATUS_BY_ID = "UPDATE specialty SET specialty_status=?, recruitment=?" +
            " WHERE specialty_id=?";
    private static final String UPDATE_SPECIALTY_BY_ID = "UPDATE specialty SET specialty_name=?, number_of_seats=? WHERE " +
            "specialty_id=?";
    private static final String FIND_ENROLLE_ID_WITH_ACCEPTED_STATUS = "SELECT enrollee_id FROM enrollee JOIN " +
            "enrollee_specialty es on enrollee.enrollee_id = es.enrollee_id_fk WHERE application_status=? and specialty_id_fk=?";
    private static final String FIND_ENROLLE_ID_WITH_ACCEPTED_AND_REJECTED_STATUS = "SELECT enrollee_id FROM enrollee JOIN " +
            "enrollee_specialty es on enrollee.enrollee_id = es.enrollee_id_fk WHERE (application_status=? or " +
            "application_status =?) and specialty_id_fk=?";
    private static final String UPDATE_ENROLLEE_APPLICATION_STATUS = "UPDATE enrollee SET application_status=? " +
            "WHERE enrollee_id=?";

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static SpecialtyDaoImpl getInstance() {
        if (instance == null) {
            instance = new SpecialtyDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Map<String, Object> parameters) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_SPECIALTY)) {
            statement.setString(1, (String) parameters.get(MapKeys.SPECIALTY_NAME));
            statement.setInt(2, (int) parameters.get(MapKeys.FACULTY_ID));
            statement.setString(3, RecruitmentType.CLOSED.getType());
            statement.setInt(4, (int) parameters.get(MapKeys.NUMBER_OF_SEATS));
            statement.setString(5, StatusType.ACTIVE.getStatus());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible add specialty", e);
            throw new DaoException("Database issues while adding specialty", e);
        }
    }

    @Override
    public boolean remove(Map<String, Object> parameters) throws DaoException {
        throw new UnsupportedOperationException("Specialty can't be removed, it can has DELETED status");
    }

    @Override
    public Optional<Specialty> findById(int specialtyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_SPECIALTY_BY_SPECIALTY_ID)) {
            statement.setInt(1, specialtyId);
            ResultSet resultSet = statement.executeQuery();
            List<Specialty> specialties = createSpecialtyListFromResultSet(resultSet);
            if (!specialties.isEmpty()) {
                Specialty specialty = specialties.get(0);
                return Optional.ofNullable(specialty);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible find specialty by id", e);
            throw new DaoException("Database issues while finding specialty by id", e);
        }
    }

    @Override
    public List<Specialty> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SPECIALTIES)) {
            statement.setString(1, RecruitmentType.OPENED.getType());
            ResultSet resultSet = statement.executeQuery();
            List<Specialty> specialties = createSpecialtyListFromResultSet(resultSet);
            return specialties;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible find all specialties", e);
            throw new DaoException("Database issues while finding all specialties", e);
        }
    }

    @Override
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
            logger.log(Level.ERROR, "Impossible update specialty", e);
            throw new DaoException("Database issues while updating specialty", e);
        }
    }

    @Override
    public List<Integer> findConsideredEnrolleeIdById(int specialtyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ENROLLE_ID_BY_SPECIALTY_ID)) {
            statement.setString(1, ApplicationStatus.CONSIDERED.getApplicationStatus());
            statement.setInt(2, specialtyId);
            ResultSet resultSet = statement.executeQuery();
            List<Integer> foundEnrolleeId = new ArrayList<>();
            while (resultSet.next()) {
                foundEnrolleeId.add(resultSet.getInt(ColumnName.ENROLLEE_ID));
            }
            return foundEnrolleeId;
        } catch (
                SQLException e) {
            logger.log(Level.ERROR, "Impossible find considered enrollee id by specialty id", e);
            throw new DaoException("Database issues while updating mark register by enrollee id", e);
        }
    }

    @Override
    public Optional<Specialty> findByEnrolleeId(int enrolleeId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_SPECIALTY_BY_ENROLLEE_ID)) {
            statement.setInt(1, enrolleeId);
            ResultSet resultSet = statement.executeQuery();
            List<Specialty> foundSpecialties = createSpecialtyListFromResultSet(resultSet);
            if (!foundSpecialties.isEmpty()) {
                return Optional.ofNullable(foundSpecialties.get(0));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible find specialty by enrollee id", e);
            throw new DaoException("Database issues while finding specialty by enrollee id", e);
        }
    }

    @Override
    public boolean updateStatusById(int specialtyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SPECIALTY_STATUS_BY_ID)) {
            statement.setString(1, StatusType.DELETED.getStatus());
            statement.setString(2, RecruitmentType.CLOSED.getType());
            statement.setInt(3, specialtyId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible update specialty status by id", e);
            throw new DaoException("Database issues while updating specialty by id", e);
        }
    }

    @Override
    public Optional<Integer> findFacultyIdBySpecialtyId(int specialtyId) throws DaoException {
        int foundFacultyId;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_FACULTY_ID_BY_SPECIALTY_ID)) {
            statement.setInt(1, specialtyId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                foundFacultyId = resultSet.getInt(FACULTY_ID);
                return Optional.ofNullable(foundFacultyId);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible find faculty id by specialty id", e);
            throw new DaoException("Database issues while finding faculty id by specialty id", e);
        }
    }

    @Override
    public List<Specialty> findOpenSpecialtiesListByFacultyId(int facultyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_OPEN_SPECIALTIES_BY_FACULTY_ID)) {
            statement.setString(1, RecruitmentType.OPENED.getType());
            statement.setInt(2, facultyId);
            ResultSet resultSet = statement.executeQuery();
            List<Specialty> foundSpecialties = createSpecialtyListFromResultSet(resultSet);
            return foundSpecialties;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible find all open specialty by faculty id ", e);
            throw new DaoException("Database issues while finding all open specialty by faculty id", e);
        }
    }

    @Override
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
            logger.log(Level.ERROR, "Impossible find all active specialty by faculty id ", e);
            throw new DaoException("Database issues while finding all active specialty by faculty id", e);
        }
    }

    @Override
    public boolean updateOpenedRecruitmentBySpecialtyId(int specialtyId, List<Integer> applications) throws DaoException {
        boolean isRecruitmentUpdate;
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        PreparedStatement specialtyStatement = null;
        PreparedStatement enrolleeStatement = null;
        try {
            connection.setAutoCommit(false);
            specialtyStatement = connection.prepareStatement(UPDATE_RECRUITMENT_BY_SPECIALTY_ID);
            enrolleeStatement = connection.prepareStatement(UPDATE_ENROLLEE_APPLICATION_STATUS);
            specialtyStatement.setString(1, RecruitmentType.CLOSED.getType());
            specialtyStatement.setInt(2, specialtyId);
            isRecruitmentUpdate = specialtyStatement.executeUpdate() > 0;
            if (isRecruitmentUpdate) {
                for (Integer enrolleeId : applications) {
                    enrolleeStatement.setString(1, ApplicationStatus.ARCHIVED.getApplicationStatus());
                    enrolleeStatement.setInt(2, enrolleeId);
                    enrolleeStatement.executeUpdate();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.log(Level.ERROR, "Impossible rollback committing", ex);
            }
            throw new DaoException("Impossible update specialty recruitment for enrollee", e);
        } finally {
            closeStatement(enrolleeStatement);
            closeStatement(specialtyStatement);
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException throwables) {
                logger.log(Level.ERROR, "Impossible return connection to pool");
            }
        }
        return isRecruitmentUpdate;
    }

    @Override
    public boolean updateClosedRecruitmentBySpecialtyId(int specialtyId) throws DaoException {
        boolean isUpdated;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_RECRUITMENT_BY_SPECIALTY_ID)) {
            statement.setString(1, RecruitmentType.OPENED.getType());
            statement.setInt(2, specialtyId);
            isUpdated = statement.executeUpdate() > 0;
            return isUpdated;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible update recruitment by specialty id", e);
            throw new DaoException("Database issues while updating recruitment by specialty id", e);
        }
    }

    @Override
    public List<Integer> findAllUnarchivedEnrolleeBySpecialtyId(int specialtyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ENROLLE_ID_WITH_ACCEPTED_AND_REJECTED_STATUS)) {
            statement.setString(1, ApplicationStatus.ACCEPTED.getApplicationStatus());
            statement.setString(2, ApplicationStatus.REJECTED.getApplicationStatus());
            statement.setInt(3, specialtyId);
            ResultSet resultSet = statement.executeQuery();
            List<Integer> foundEnrolleeId = new ArrayList<>();
            while (resultSet.next()) {
                foundEnrolleeId.add(resultSet.getInt(ColumnName.ENROLLEE_ID));
            }
            return foundEnrolleeId;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible find enrollee with accepted and rejected status by specialty id", e);
            throw new DaoException("Database issues while finding enrollee with accepted  and rejected status by " +
                    "specialty id", e);
        }
    }

    @Override
    public int findAllEnrolleeWithAcceptedApplicationStatus(int specialtyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ENROLLE_ID_WITH_ACCEPTED_STATUS)) {
            statement.setString(1, ApplicationStatus.ACCEPTED.getApplicationStatus());
            statement.setInt(2, specialtyId);
            ResultSet resultSet = statement.executeQuery();
            List<Integer> foundEnrolleeId = new ArrayList<>();
            while (resultSet.next()) {
                foundEnrolleeId.add(resultSet.getInt(ColumnName.ENROLLEE_ID));
            }
            return foundEnrolleeId.size();
        } catch (
                SQLException e) {
            logger.log(Level.ERROR, "Impossible find enrollee with accepted status specialty id", e);
            throw new DaoException("Database issues while finding enrollee with accepted status by specialty id", e);
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
}