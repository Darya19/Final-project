package com.epam.enrollee.model.dao.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.connector.ConnectionPool;
import com.epam.enrollee.model.dao.BaseDao;
import com.epam.enrollee.model.dao.ColumnName;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EnrolleeMarkRegisterDaoImpl implements BaseDao<EnrolleeMarkRegister> {

    public static EnrolleeMarkRegisterDaoImpl instance;
    private static final String FIND_ENROLLEE_REGISTER_BY_ENROLLE_ID =
            "SELECT subject_id, subject_name, mark_value FROM subject JOIN mark on subject.subject_id " +
                    "= mark.subject_id_fk WHERE enrollee_id_fk=?";
    private static final String UPDATE_ENROLLEE_MARK_REGISTER =
            "UPDATE mark SET subject_id_fk=?, mark_value=? where enrollee_id_fk=?";

    public static EnrolleeMarkRegisterDaoImpl getInstance() {
        if (instance == null) {
            instance = new EnrolleeMarkRegisterDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Map<String, Object> parameters) throws DaoException {
        return false;
    }

    @Override
    public boolean remove(EnrolleeMarkRegister enrolleeMarkRegister) throws DaoException {
        return false;
    }

    public boolean update(EnrolleeMarkRegister register, int enrolleeId) throws DaoException {
        Map<Subject, Integer> parameters = register.getTestsSubjectsAndMarks();
        int countUpdate = 0;
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ENROLLEE_MARK_REGISTER)) {
            for (Map.Entry<Subject, Integer> pair : parameters.entrySet()) {
                Subject subject = pair.getKey();
                int markValue = pair.getValue();
                statement.setInt(1, subject.getSubjectId());
                statement.setInt(2, markValue);
                statement.setInt(3, enrolleeId);
                countUpdate += statement.executeUpdate();
            }
               if(countUpdate == 4){
                   return true;
               } else {return false;}
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    @Override
    public Optional<EnrolleeMarkRegister> findById(int parameter) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<EnrolleeMarkRegister> findAll() throws DaoException {
        return null;
    }

    public Optional<EnrolleeMarkRegister> findEnrolleeMarkRegisterByEnrolleeId(int enrolleeId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ENROLLEE_REGISTER_BY_ENROLLE_ID)) {
            statement.setInt(1, enrolleeId);
            ResultSet resultSet = statement.executeQuery();
            EnrolleeMarkRegister register = createEnrolleeRegisterFromResultSet(resultSet);
            if (register != null) {
                return Optional.of(register);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
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

