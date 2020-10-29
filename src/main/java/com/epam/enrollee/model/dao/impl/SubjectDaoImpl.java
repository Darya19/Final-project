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

    private static final String FIND_ALL_SUBJECTS =
            "SELECT subject_id , subject_name FROM subject";
    private static final String FIND_SUBJECTS_BY_SPECIALTY_NAME =
            "SELECT subject_id, subject_name FROM subject_specialty JOIN subject ON subject.subject_id " +
                    "= subject_specialty.subject_id_fk JOIN specialty s on s.specialty_id = subject_specialty.specialty_id_fk " +
                    "WHERE specialty_name=?";

    @Override
    public boolean add(Map<String, Object> parameters) throws DaoException {
        return true;
    }

    @Override
    public boolean remove(Subject subject) throws DaoException {
        return true;
    }

    @Override
    public Optional<List<Subject>> findById(int parameter) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<List<Subject>> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SUBJECTS)) {
            ResultSet resultSet = statement.executeQuery();
            List<Subject> foundSubjects = createSubjectListFromResultSet(resultSet);
            if (!foundSubjects.isEmpty()) {
                return Optional.of(foundSubjects);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }
//check
    public Optional<List<Subject>> findSubjectsBySpecialtyName(String specialtyName) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_SUBJECTS_BY_SPECIALTY_NAME)) {
            statement.setString(1, specialtyName);
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
