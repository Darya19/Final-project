package com.epam.enrollee.model.dao.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.connector.ConnectionPool;
import com.epam.enrollee.model.dao.BaseDao;
import com.epam.enrollee.model.entity.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.enrollee.model.dao.ColumnName.SUBJECT_ID;
import static com.epam.enrollee.model.dao.ColumnName.SUBJECT_NAME;

public class SubjectDaoImpl implements BaseDao<Subject> {

    public static SubjectDaoImpl instance;
    private static final String FIND_ALL_SUBJECTS =
            "SELECT subject_id , subject_name FROM subject";
    private static final String FIND_SUBJECTS_BY_SPECIALTY_ID =
            "SELECT subject_id, subject_name FROM subject JOIN subject_specialty ON subject.subject_id " +
                    "= subject_specialty.subject_id_fk WHERE specialty_id_fk=?";
    private static final String FIND_SUBJECTS_BY_SUBJECT_ID =
            "SELECT subject_id, subject_name FROM subject WHERE subject_id=?";

    public static SubjectDaoImpl getInstance() {
        if (instance == null) {
            instance = new SubjectDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Map<String, Object> parameters) throws DaoException {
        return true;
    }

    @Override
    public boolean remove(Map<String, Object> parameters) throws DaoException {
        return true;
    }

    public boolean update(Subject subject) throws DaoException {
        return false;
    }
//update mark
    @Override
    public Optional<Subject> findById(int subjectId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_SUBJECTS_BY_SUBJECT_ID)) {
            statement.setInt(1, subjectId);
            ResultSet resultSet = statement.executeQuery();
            List<Subject> subjects = createSubjectListFromResultSet(resultSet);
            if (!subjects.isEmpty()) {
                return Optional.of(subjects.get(0));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    @Override
    public List<Subject> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SUBJECTS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Subject> foundSubjects = createSubjectListFromResultSet(resultSet);
                return foundSubjects;
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    //register
    public Optional<List<Subject>> findSubjectsBySpecialtyId(int specialtyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_SUBJECTS_BY_SPECIALTY_ID)) {
            statement.setInt(1, specialtyId);
            ResultSet resultSet = statement.executeQuery();
            List<Subject> subjects = createSubjectListFromResultSet(resultSet);
            if (!subjects.isEmpty()) {
                return Optional.of(subjects);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    private List<Subject> createSubjectListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Subject> subjects = new ArrayList<>();
        while (resultSet.next()) {
            Subject subject = new Subject();
            subject.setSubjectId(resultSet.getInt(SUBJECT_ID));
            subject.setSubjectName(resultSet.getString(SUBJECT_NAME));
            subjects.add(subject);
        }
        return subjects;
    }
}
