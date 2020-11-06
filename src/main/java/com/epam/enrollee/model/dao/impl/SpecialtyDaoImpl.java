package com.epam.enrollee.model.dao.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.connector.ConnectionPool;
import com.epam.enrollee.model.dao.BaseDao;
import com.epam.enrollee.model.dao.ColumnName;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.type.RecruitmentType;
import com.epam.enrollee.model.type.StatusType;

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
                    " FROM specialty ";
    private static final String FIND_FACULTY_ID_BY_SPECIALTY_ID =
            "SELECT faculty_id_fk as faculty_id FROM  specialty WHERE specialty_id=?";
    private static final String FIND_SPECIALTY_BY_SPECIALTY_ID =
            "SELECT specialty_id, specialty_name, recruitment, number_of_seats, specialty_status" +
                    " FROM specialty WHERE specialty_id=?";
    private static final String FIND_SPECIALTIES_BY_FACULTY_ID =
            "SELECT specialty_id, specialty_name, recruitment, number_of_seats, specialty_status" +
                    " FROM specialty WHERE faculty_id_fk=?";

    public static SpecialtyDaoImpl getInstance() {
        if (instance == null) {
            instance = new SpecialtyDaoImpl();
        }
        return instance;
    }

    @Override
    public boolean add(Map<String, Object> parameters) throws DaoException {
        return true;
    }

    @Override
    public boolean remove(Specialty specialty) throws DaoException {
        return true;
    }

    public boolean  update(Specialty specialty) throws DaoException {
        return false;
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
                return Optional.of(specialty);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new DaoException("database issues", e);
        }
    }

    //check
    @Override
    public List<Specialty> findAll() throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SPECIALTIES)) {
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

    public List<Specialty> findSpecialtiesListByFacultyId(int facultyId) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_SPECIALTIES_BY_FACULTY_ID)) {
            statement.setInt(1, facultyId);
            ResultSet resultSet = statement.executeQuery();
            List<Specialty> foundSpecialties = createSpecialtyListFromResultSet(resultSet);
                return foundSpecialties;
        } catch (SQLException e) {
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
}
