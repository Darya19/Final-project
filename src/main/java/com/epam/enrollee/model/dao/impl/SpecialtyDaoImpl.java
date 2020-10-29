package com.epam.enrollee.model.dao.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.connector.ConnectionPool;
import com.epam.enrollee.model.dao.BaseDao;
import com.epam.enrollee.model.dao.ColumnName;
import com.epam.enrollee.model.entity.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.epam.enrollee.model.dao.ColumnName.*;

public class SpecialtyDaoImpl implements BaseDao<Specialty> {

    private static final String FIND_SUBJECTS_BY_SPECIALTY_NAME =
            "SELECT subject_id, subject_name FROM subject_specialty JOIN subject ON subject.subject_id " +
                    "= subject_specialty.subject_id_fk JOIN specialty on specialty.specialty_id " +
                    "= subject_specialty.specialty_id_fk WHERE specialty_name=?";
    private static final String FIND_ALL_SPECIALTIES =
            "SELECT specialty_id, specialty_name, GROUP_CONCAT(subject_id_fk ORDER BY subject_id_fk ASC separator ',') " +
                    "AS subject_id FROM specialty LEFT JOIN subject_specialty" +
                    " ON specialty.specialty_id = subject_specialty.specialty_id_fk GROUP BY specialty_id";
    private static final String FIND_FACULTY_ID_BY_SPECIALTY_ID =
            "SELECT faculty_id_fk as faculty_id FROM  specialty WHERE specialty_id=?";

    @Override
    public boolean add(Map<String,Object> parameters) throws DaoException {
        return true;
    }

    @Override
    public boolean remove(Specialty specialty) throws DaoException {
        return true;
    }

    @Override
    public Optional<List<Specialty>> findById(int parameter) throws DaoException {
        return Optional.empty();
    }
//check
    @Override
    public Optional<List<Specialty>> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SPECIALTIES)) {
            ResultSet resultSet = statement.executeQuery();
            List<Specialty> specialties = createSpecialtyListFromResultSet(resultSet);
            if (!specialties.isEmpty()) {
                return Optional.of(specialties);
            } else {
                return Optional.empty();
            }
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

    private List<Specialty> createSpecialtyListFromResultSet(ResultSet resultSet) throws SQLException {
        List<Specialty> specialties = new ArrayList<>();
        while (resultSet.next()) {
            Specialty specialty = new Specialty();
            specialty.setSpecialtyId(resultSet.getInt(ColumnName.SPECIALTY_ID));
            specialty.setSpecialtyName(resultSet.getString(SPECIALTY_NAME));
            String[] subjects = resultSet.getString(SUBJECT_ID).split(",");
            List<String> subjectsList = Arrays.asList(subjects);
            for (String id : subjectsList) {
                specialty.add(Integer.valueOf(id));
            }
            specialties.add(specialty);
        }
        return specialties;
    }


}
