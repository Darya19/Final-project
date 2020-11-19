package com.epam.enrollee.model.dao.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.connector.ConnectionPool;
import com.epam.enrollee.model.dao.ColumnName;
import com.epam.enrollee.model.dao.EnrolleeMarkRegisterDao;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Subject;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Enrollee mark register dao.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class EnrolleeMarkRegisterDaoImpl implements EnrolleeMarkRegisterDao {

    /**
     * The constant instance.
     */
    public static EnrolleeMarkRegisterDaoImpl instance;
    private static Logger logger = LogManager.getLogger();
    private static final String FIND_ENROLLEE_REGISTER_BY_ENROLLEE_ID = "SELECT subject_id, subject_name, mark_value " +
            "FROM subject JOIN mark on subject.subject_id = mark.subject_id_fk WHERE enrollee_id_fk=?";
    private static final String UPDATE_ENROLLEE_MARK_REGISTER = "UPDATE mark SET mark_value=? WHERE enrollee_id_fk=? " +
            "and subject_id_fk=?";
    private static final String UPDATE_ENROLLEE_REGISTER = "UPDATE mark SET mark_value=?, subject_id_fk=? WHERE " +
            "enrollee_id_fk=? and subject_id_fk=?";

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static EnrolleeMarkRegisterDaoImpl getInstance() {
        if (instance == null) {
            instance = new EnrolleeMarkRegisterDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean update(EnrolleeMarkRegister register, int enrolleeId) throws DaoException {
        Map<Subject, Integer> parameters = register.getTestsSubjectsAndMarks();
        int countUpdate = 0;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ENROLLEE_MARK_REGISTER)) {
            for (Map.Entry<Subject, Integer> pair : parameters.entrySet()) {
                Subject subject = pair.getKey();
                int markValue = pair.getValue();
                statement.setInt(1, markValue);
                statement.setInt(2, enrolleeId);
                statement.setInt(3, subject.getSubjectId());
                countUpdate += statement.executeUpdate();
            }
            return countUpdate == 4;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible update mark register by enrollee id", e);
            throw new DaoException("Database issues while updating mark register by enrollee id", e);
        }
    }

    @Override
    public boolean update(EnrolleeMarkRegister register, int enrolleeId, List<Subject> subjectsForUpdate) throws DaoException {
        Map<Subject, Integer> parameters = register.getTestsSubjectsAndMarks();
        int countUpdate = 0;
        int countSubjects = 0;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ENROLLEE_REGISTER)) {
            for (Map.Entry<Subject, Integer> pair : parameters.entrySet()) {
                Subject subject = pair.getKey();
                int markValue = pair.getValue();
                statement.setInt(1, markValue);
                statement.setInt(2, subject.getSubjectId());
                statement.setInt(3, enrolleeId);
                statement.setInt(4, subjectsForUpdate.get(countSubjects).getSubjectId());
                countUpdate += statement.executeUpdate();
                countSubjects++;
            }
            return countUpdate == 4;
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible update mark register by enrollee id", e);
            throw new DaoException("Database issues while updating mark register by enrollee id", e);
        }
    }

    @Override
    public Optional<EnrolleeMarkRegister> findEnrolleeMarkRegisterByEnrolleeId(int enrolleeId)
            throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement
                     (FIND_ENROLLEE_REGISTER_BY_ENROLLEE_ID)) {
            statement.setInt(1, enrolleeId);
            ResultSet resultSet = statement.executeQuery();
            EnrolleeMarkRegister register = createEnrolleeRegisterFromResultSet(resultSet);
            if (!register.getTestsSubjectsAndMarks().isEmpty()) {
                return Optional.of(register);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Impossible find mark register by enrollee id", e);
            throw new DaoException("Database issues while finding mark register by enrollee id", e);
        }
    }

    private EnrolleeMarkRegister createEnrolleeRegisterFromResultSet(ResultSet resultSet) throws SQLException {
        EnrolleeMarkRegister register = new EnrolleeMarkRegister();
        while (resultSet.next()) {
            Subject subject = new Subject();
            subject.setSubjectId(resultSet.getInt(ColumnName.SUBJECT_ID));
            subject.setSubjectName(resultSet.getString(ColumnName.SUBJECT_NAME));
            register.put(subject, resultSet.getInt(ColumnName.MARK_VALUE));
        }
        return register;
    }
}