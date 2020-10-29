package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.*;
import com.epam.enrollee.model.entity.*;
import com.epam.enrollee.model.enumtype.RoleType;
import com.epam.enrollee.model.enumtype.StatusType;
import com.epam.enrollee.parser.NumberParser;
import com.epam.enrollee.util.PasswordEncryptor;
import com.epam.enrollee.validator.EnrolleeValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.*;

import static com.epam.enrollee.controller.command.RequestParameters.*;

public class EnrolleeServiceImpl {

    UserDaoImpl userDao = new UserDaoImpl();
    EnrolleeDaoImpl enrolleeDao = new EnrolleeDaoImpl();
    private Logger logger = LogManager.getLogger();

    public Optional<Enrollee> findEnrolleeByEmail(String email) throws ServiceException {
        try {
            Optional<Enrollee> foundEnrollee = enrolleeDao.findEnrolleeByEmail(email);
            return foundEnrollee;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean checkEmailAndPassword(String email, String password) throws ServiceException {
        try {
            String foundPassword = userDao.findPasswordByEmail(email).get();
            if (!foundPassword.isEmpty()) {
                String hashPassword = PasswordEncryptor.encryptPassword(password);
                if (hashPassword.equals(foundPassword)) {
                    return true;
                }
            }
            return false;
        } catch (DaoException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<User> findUserByEmail(String email) throws ServiceException {
        try {
            Optional<User> foundUser = userDao.findUserByEmail(email);
            return foundUser;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Map<String, String> checkEnrolleeParameters(Map<String, String> parameters) throws ServiceException {
        EnrolleeValidator validator = new EnrolleeValidator();
        FacultyDaoImpl facultyDao = new FacultyDaoImpl();
        SpecialtyDaoImpl specialtyDao = new SpecialtyDaoImpl();
        SubjectDaoImpl subjectDao = new SubjectDaoImpl();
        parameters = validator.validateRegistrationData(parameters);
        try {
            Optional<User> user = userDao.findUserByEmail(parameters.get(EMAIL));
            Optional<Integer> optionalFacultyId = specialtyDao.findFacultyIdBySpecialtyId
                    (Integer.parseInt(parameters.get(RequestParameters.SPECIALTY_ID)));
            Optional<List<Subject>> optionalSubjects = subjectDao.findSubjectsBySpecialtyName
                    (parameters.get(RequestParameters.SPECIALTY_ID));
            if (user.isPresent()) {
                parameters.put(EMAIL, "");
            }
            if (!parameters.get(PASSWORD).equals(parameters.get(REPEATED_PASSWORD))) {
                parameters.put(REPEATED_PASSWORD, "");
                parameters.put(PASSWORD, "");
            }
            int foundFaculty;
            if (optionalFacultyId.isPresent()) {
                foundFaculty = optionalFacultyId.get();
                parameters.put(FACULTY_ID, String.valueOf(foundFaculty));
            } else {
                parameters.put(FACULTY_ID, "");
                parameters.put(SPECIALTY_ID, "");
            }

            if (optionalSubjects.isPresent()) {
                List<Subject> foundSubjects = optionalSubjects.get();
                List<String> subjectNames = new ArrayList<>();
                for (Subject subject : foundSubjects) {
                    subjectNames.add(subject.getSubjectName());
                }
                if (!subjectNames.contains(parameters.get(SUBJECT_ID_1))
                        || !subjectNames.contains(parameters.get(SUBJECT_ID_2))
                        || !subjectNames.contains(parameters.get(SUBJECT_ID_3))) {
                    parameters.put(SUBJECT_ID_1, "");
                    parameters.put(SUBJECT_ID_2, "");
                    parameters.put(SUBJECT_ID_3, "");
                }
            } else {
                parameters.put(SUBJECT_ID_1, "");
                parameters.put(SUBJECT_ID_2, "");
                parameters.put(SUBJECT_ID_3, "");
            }
            return parameters;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    public Optional<Map<String, Object>> create(Map<String, String> parameters) throws ServiceException {
        NumberParser parser = new NumberParser();
        Map<String, Object> createdObjects = new HashMap<>();
        Enrollee enrollee = new Enrollee();
        Passport passport = new Passport();
        int facultyId = parser.parseToInt(parameters.get(FACULTY_ID));
        int specialtyId = parser.parseToInt(parameters.get(SPECIALTY_ID));
        int subjectId1 = parser.parseToInt(parameters.get(SUBJECT_ID_1));
        int subjectId2 = parser.parseToInt(parameters.get(SUBJECT_ID_2));
        int subjectId3 = parser.parseToInt(parameters.get(SUBJECT_ID_3));
        int subjectId4 = parser.parseToInt(parameters.get(SUBJECT_ID_4));
        int markValue1 = parser.parseToInt(parameters.get(MARK_1));
        int markValue2 = parser.parseToInt(parameters.get(MARK_2));
        int markValue3 = parser.parseToInt(parameters.get(MARK_3));
        int markValue4 = parser.parseToInt(parameters.get(MARK_4));
        enrollee.setEmail(parameters.get(EMAIL));
        enrollee.setRole(RoleType.USER);
        enrollee.setStatus(StatusType.ACTIVE);
        enrollee.setFirstName(parameters.get(FIRST_NAME));
        enrollee.setLastName(parameters.get(LAST_NAME));
        enrollee.setMiddleName(parameters.get(MIDDLE_NAME));
        enrollee.setChosenFacultyId(facultyId);
        enrollee.setChosenSpecialtyId(specialtyId);
        enrollee.put(subjectId1, markValue1);
        enrollee.put(subjectId2, markValue2);
        enrollee.put(subjectId3, markValue3);
        enrollee.put(subjectId4, markValue4);
        createdObjects.put(ENROLLEE, enrollee);
        passport.setPersonalNumber(parameters.get(PERSONAL_NUMBER));
        passport.setPassportSeriesAndNumber(parameters.get(PASSPORT_SERIES_AND_NUMBER));
        try {
            enrolleeDao.add(createdObjects);
            return Optional.of(createdObjects);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}