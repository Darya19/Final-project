package com.epam.enrollee.model.dao.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.connector.ConnectionPool;
import com.epam.enrollee.model.dao.BaseDao;
import com.epam.enrollee.model.dao.ColumnName;
import com.epam.enrollee.model.entity.Address;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Passport;
import com.epam.enrollee.model.enumtype.RoleType;
import com.epam.enrollee.model.enumtype.StatusType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

import static com.epam.enrollee.controller.command.RequestParameters.*;
import static com.epam.enrollee.model.dao.ColumnName.MARK_VALUE;
import static com.epam.enrollee.model.dao.ColumnName.SUBJECT_ID;

public class EnrolleeDaoImpl implements BaseDao<Enrollee> {

    private Logger logger = LogManager.getLogger();

    private static final String FIND_ENROLLEE_BY_EMAIL =
            "SELECT user_id,email,role,status,enrollee_first_name,enrollee_last_name,enrollee_middle_name," +
                    "faculty_id_fk as faculty_id,specialty_id_fk as specialty_id, GROUP_CONCAT(subject_id_fk " +
                    "SEPARATOR ',') as subject_id, GROUP_CONCAT(mark_value SEPARATOR ',') as mark_value FROM " +
                    "enrollee JOIN user on user.user_id = enrollee.enrollee_id JOIN enrollee_faculty on " +
                    "enrollee.enrollee_id = enrollee_faculty.enrollee_id_fk JOIN enrollee_specialty on " +
                    "enrollee.enrollee_id = enrollee_specialty.enrollee_id_fk JOIN mark on enrollee.enrollee_id" +
                    "= mark.enrollee_id_fk WHERE email=?";
    private static final String ADD_ENROLLEE =
            "INSERT INTO enrollee (enrollee_first_name, enrollee_last_name, enrollee_middle_name, " +
                    "address_id_fk, passport_id_fk, application_status) " +
                    "VALUES (?,?,?,?,?,?)";
    private static final String ADD_ADDRESS =
            "INSERT INTO address (country, city, street, house_number, flat_number, telephone_number) " +
                    "VALUES (?,?,?,?,?,?)";
    private static final String ADD_PASSPORT =
            "INSERT INTO passport (personal_number, passport_series_and_number) " +
                    "VALUES (?,?)";
    private static final String ADD_MARKS =
            "INSERT INTO mark (enrollee_id_fk, subject_id_fk, mark_value) " +
                    "VALUES (?,?,?)";
    private static final String ADD_ENROLLEE_IN_SPECIALTY =
            "INSERT INTO enrollee_specialty (enrollee_id_fk, specialty_id_fk) " +
                    "VALUES (?,?)";
    private static final String ADD_ENROLLEE_IN_FACULTY =
            "INSERT INTO enrollee_faculty (enrollee_id_fk,faculty_id_fk) " +
                    "VALUES (?,?)";

    @Override
    public boolean add(Map<String, Object> objectMap) throws DaoException {
        boolean isEnrolleeAdded = false;
        boolean isAddressAdded;
        boolean isPassportAdded;
        int passportId = 0;
        int addressId = 0;
        int enrolleeId = 0;
        Enrollee enrollee = (Enrollee) objectMap.get(ENROLLEE);
        Address address = (Address) objectMap.get(ADDRESS);
        Passport passport = (Passport) objectMap.get(PASSPORT);
        Connection connection = ConnectionPool.INSTANCE.getConnection();
        PreparedStatement enrolleeStatement = null;
        PreparedStatement passportStatement = null;
        PreparedStatement addressStatement = null;
        PreparedStatement markStatement = null;
        PreparedStatement specialtyStatement = null;
        PreparedStatement facultyStatement = null;
        try {
            connection.setAutoCommit(false);
            enrolleeStatement = connection.prepareStatement(ADD_ENROLLEE, Statement.RETURN_GENERATED_KEYS);
            passportStatement = connection.prepareStatement(ADD_PASSPORT, Statement.RETURN_GENERATED_KEYS);
            addressStatement = connection.prepareStatement(ADD_ADDRESS, Statement.RETURN_GENERATED_KEYS);
            markStatement = connection.prepareStatement(ADD_MARKS);
            specialtyStatement = connection.prepareStatement(ADD_ENROLLEE_IN_SPECIALTY);
            facultyStatement = connection.prepareStatement(ADD_ENROLLEE_IN_FACULTY);
            passportStatement.setString(1, passport.getPersonalNumber());
            passportStatement.setString(2, passport.getPassportSeriesAndNumber());
            isPassportAdded = passportStatement.executeUpdate() > 0;
            ResultSet passportResultSet = passportStatement.getGeneratedKeys();
            if (passportResultSet.next()) {
                passportId = passportResultSet.getInt(1);
            }
            addressStatement.setString(1, address.getCountry());
            addressStatement.setString(2, address.getCity());
            addressStatement.setString(3, address.getStreet());
            addressStatement.setString(4, address.getHouseNumber());
            addressStatement.setString(5, address.getFlatNumber());
            addressStatement.setString(6, address.getPhoneNumber());
            isAddressAdded = addressStatement.executeUpdate() > 0;
            ResultSet addressResultSet = passportStatement.getGeneratedKeys();
            if (addressResultSet.next()) {
                addressId = addressResultSet.getInt(1);
            }
            if (isAddressAdded && isPassportAdded) {
                enrolleeStatement.setString(1, enrollee.getFirstName());
                enrolleeStatement.setString(2, enrollee.getLastName());
                enrolleeStatement.setString(3, enrollee.getMiddleName());
                enrolleeStatement.setInt(4, addressId);
                enrolleeStatement.setInt(1, passportId);
                isEnrolleeAdded = enrolleeStatement.executeUpdate() > 0;
                ResultSet enrolleeResultSet = passportStatement.getGeneratedKeys();
                if (enrolleeResultSet.next()) {
                    enrolleeId = addressResultSet.getInt(1);
                }
                if(isEnrolleeAdded){
                    facultyStatement.setInt(1,enrolleeId);
                    facultyStatement.setInt(2, enrollee.getChosenFacultyId());
                    facultyStatement.executeUpdate();
                    specialtyStatement.setInt(1,enrolleeId);
                    specialtyStatement.setInt(2, enrollee.getChosenSpecialtyId());
                    specialtyStatement.executeUpdate();
                    for(Map.Entry<Integer, Integer> pair : enrollee.getTestsSubjectsAndMarks().entrySet()){
                        int subjectId = pair.getKey();
                        int markValue = pair.getValue();
                        markStatement.setInt(1,enrolleeId);
                        markStatement.setInt(2, subjectId);
                        markStatement.setInt(3, markValue);
                        markStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                logger.log(Level.ERROR, "impossible rollback committing", ex);
            }
            throw new DaoException("impossible add new enrollee", e);
        } finally {
            closeStatement(enrolleeStatement);
            closeStatement(addressStatement);
            closeStatement(passportStatement);
            closeStatement(facultyStatement);
            closeStatement(specialtyStatement);
            closeStatement(markStatement);
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
       return isEnrolleeAdded;
    }

    @Override
    public boolean remove(Enrollee enrollee) throws DaoException {
        return true;
    }

    @Override
    public Optional<List<Enrollee>> findById(int parameter) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<List<Enrollee>> findAll() throws DaoException {
        return Optional.empty();
    }

    public Optional<Enrollee> findEnrolleeByEmail(String email) throws DaoException {
        try (Connection connection = ConnectionPool.INSTANCE.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ENROLLEE_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            List<Enrollee> enrollees = createEnrolleeListFromResultSet(resultSet);
            if (enrollees.isEmpty()) {
                Enrollee enrollee = enrollees.get(0);
                return Optional.of(enrollee);
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
            enrollee.setUserId(resultSet.getInt(ColumnName.USER_ID));
            enrollee.setEmail(resultSet.getString(ColumnName.EMAIL));
            enrollee.setRole(RoleType.valueOf(resultSet.getString(ColumnName.ROLE).toUpperCase()));
            enrollee.setStatus(StatusType.valueOf(resultSet.getString(ColumnName.STATUS).toUpperCase()));
            enrollee.setFirstName(resultSet.getString(ColumnName.ENROLLEE_FIRST_NAME));
            enrollee.setLastName(resultSet.getString(ColumnName.ENROLLEE_LAST_NAME));
            enrollee.setMiddleName(resultSet.getString(ColumnName.ENROLLEE_MIDDLE_NAME));
            enrollee.setChosenFacultyId(resultSet.getInt(ColumnName.FACULTY_ID));
            enrollee.setChosenSpecialtyId(resultSet.getInt(ColumnName.SPECIALTY_ID));
            enrollee.put(resultSet.getInt(ColumnName.SUBJECT_ID), resultSet.getInt(ColumnName.MARK_VALUE));
            enrollees.add(enrollee);
            String[] subjects = resultSet.getString(SUBJECT_ID).split(",");
            List<String> subjectsList = Arrays.asList(subjects);
            String[] marks = resultSet.getString(MARK_VALUE).split(",");
            List<String> marksList = Arrays.asList(marks);
            for (int i = 0; i < subjectsList.size(); i++) {
                enrollee.put(Integer.valueOf(subjectsList.get(i)), Integer.valueOf(marksList.get(i)));
            }
            enrollees.add(enrollee);
        }
        return enrollees;
    }
}
