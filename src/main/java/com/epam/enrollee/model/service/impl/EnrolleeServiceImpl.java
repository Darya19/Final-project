package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.EnrolleeDaoImpl;
import com.epam.enrollee.model.dao.impl.SpecialtyDaoImpl;
import com.epam.enrollee.model.dao.impl.SubjectDaoImpl;
import com.epam.enrollee.model.dao.impl.UserDaoImpl;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Passport;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.entity.User;
import com.epam.enrollee.model.type.ApplicationStatus;
import com.epam.enrollee.model.type.RoleType;
import com.epam.enrollee.model.type.StatusType;
import com.epam.enrollee.parser.NumberParser;
import com.epam.enrollee.util.MapKeys;
import com.epam.enrollee.util.PasswordEncryptor;
import com.epam.enrollee.validator.EnrolleeValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.*;

public class EnrolleeServiceImpl {

    private static Logger logger = LogManager.getLogger();

    public Optional<Enrollee> createEnrolleeByEmail(String email) throws ServiceException {
        EnrolleeDaoImpl enrolleeDao = EnrolleeDaoImpl.getInstance();
        try {
            Optional<Enrollee> foundEnrollee = enrolleeDao.findEnrolleeByEmail(email);
            return foundEnrollee;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean checkEmailAndPassword(String email, String password) throws ServiceException {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            Optional<String> optionalPassword = userDao.findPasswordByEmail(email);
            if (optionalPassword.isPresent()) {
                String foundPassword = optionalPassword.get();
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
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            Optional<User> foundUser = userDao.findUserByEmail(email);
            return foundUser;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Map<String, String> checkEnrolleeParameters(Map<String, String> parameters) throws ServiceException {
        EnrolleeValidator validator = new EnrolleeValidator();
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        SpecialtyDaoImpl specialtyDao = SpecialtyDaoImpl.getInstance();
        SubjectDaoImpl subjectDao = SubjectDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        parameters = validator.validateRegistrationData(parameters);
        try {
            if (parameters.containsKey(MapKeys.EMAIL) && !parameters.get(MapKeys.EMAIL).isEmpty()) {
                Optional<User> user = userDao.findUserByEmail(parameters.get(MapKeys.EMAIL));
                if (user.isPresent()) {
                    parameters.put(MapKeys.EMAIL, "");
                }
            }
            if (parameters.containsKey(MapKeys.PASSWORD) && (parameters.containsKey(MapKeys.REPEATED_PASSWORD)
                    && !parameters.get(MapKeys.PASSWORD).isEmpty()
                    && !parameters.get(MapKeys.REPEATED_PASSWORD).isEmpty())) {
                if (!parameters.get(MapKeys.PASSWORD).equals(parameters.get(MapKeys.REPEATED_PASSWORD))) {
                    parameters.put(MapKeys.REPEATED_PASSWORD, "");
                    parameters.put(MapKeys.PASSWORD, "");
                }
            }
            if (parameters.containsKey(MapKeys.SPECIALTY_ID)
                    && !parameters.get(MapKeys.SPECIALTY_ID).isEmpty()) {
                int intSpecialtyId = parser.parseToInt(parameters.get(MapKeys.SPECIALTY_ID));
                int foundFaculty;
                Optional<Integer> optionalFacultyId = specialtyDao.findFacultyIdBySpecialtyId(intSpecialtyId);
                Optional<List<Subject>> optionalSubjects = subjectDao.findSubjectsBySpecialtyId(intSpecialtyId);
                if (optionalFacultyId.isPresent()) {
                    foundFaculty = optionalFacultyId.get();
                    parameters.put(MapKeys.FACULTY_ID, String.valueOf(foundFaculty));
                } else {
                    parameters.put(MapKeys.FACULTY_ID, "");
                }
                if (optionalSubjects.isPresent()) {
                    List<Subject> foundSubjects = optionalSubjects.get();
                    Set<String> subjectsId = new HashSet<>();
                    for (int i = 0; i < foundSubjects.size(); i++) {
                        subjectsId.add(String.valueOf(foundSubjects.get(i).getSubjectId()));
                    }
                    if (!subjectsId.contains(parameters.get(MapKeys.SUBJECT_ID_1))
                            || !subjectsId.contains(parameters.get(MapKeys.SUBJECT_ID_2))
                            || !subjectsId.contains(parameters.get(MapKeys.SUBJECT_ID_3))
                            || !subjectsId.contains(parameters.get(MapKeys.SUBJECT_ID_4))) {
                        parameters.put(MapKeys.SUBJECT_ID_1, "");
                        parameters.put(MapKeys.SUBJECT_ID_2, "");
                        parameters.put(MapKeys.SUBJECT_ID_3, "");
                        parameters.put(MapKeys.SUBJECT_ID_4, "");
                    }
                } else {
                    parameters.put(MapKeys.SUBJECT_ID_1, "");
                    parameters.put(MapKeys.SUBJECT_ID_2, "");
                    parameters.put(MapKeys.SUBJECT_ID_3, "");
                    parameters.put(MapKeys.SUBJECT_ID_4, "");
                }
            }
            return parameters;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean registerEnrollee(Map<String, String> parameters) throws ServiceException {
        EnrolleeDaoImpl enrolleeDao = EnrolleeDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        Map<String, Object> createdObjects = new HashMap<>();
        try {
            int facultyId = parser.parseToInt(parameters.get(MapKeys.FACULTY_ID));
            int specialtyId = parser.parseToInt(parameters.get(MapKeys.SPECIALTY_ID));
            int subjectId1 = parser.parseToInt(parameters.get(MapKeys.SUBJECT_ID_1));
            int subjectId2 = parser.parseToInt(parameters.get(MapKeys.SUBJECT_ID_2));
            int subjectId3 = parser.parseToInt(parameters.get(MapKeys.SUBJECT_ID_3));
            int subjectId4 = parser.parseToInt(parameters.get(MapKeys.SUBJECT_ID_4));
            int markValue1 = parser.parseToInt(parameters.get(MapKeys.MARK_1));
            int markValue2 = parser.parseToInt(parameters.get(MapKeys.MARK_2));
            int markValue3 = parser.parseToInt(parameters.get(MapKeys.MARK_3));
            int markValue4 = parser.parseToInt(parameters.get(MapKeys.MARK_4));
            String password = PasswordEncryptor.encryptPassword(parameters.get(MapKeys.PASSWORD));
            createdObjects.put(MapKeys.EMAIL, parameters.get(MapKeys.EMAIL));
            createdObjects.put(MapKeys.PASSWORD, password);
            createdObjects.put(MapKeys.ROLE, RoleType.USER.getRole());
            createdObjects.put(MapKeys.ENROLLEE_STATUS, StatusType.ACTIVE.getStatus());
            createdObjects.put(MapKeys.FIRST_NAME, parameters.get(MapKeys.FIRST_NAME));
            createdObjects.put(MapKeys.LAST_NAME, parameters.get(MapKeys.LAST_NAME));
            createdObjects.put(MapKeys.MIDDLE_NAME, parameters.get(MapKeys.MIDDLE_NAME));
            createdObjects.put(MapKeys.APPLICATION_STATUS, ApplicationStatus.CONSIDERED.getApplicationStatus());
            createdObjects.put(MapKeys.FACULTY_ID, facultyId);
            createdObjects.put(MapKeys.SPECIALTY_ID, specialtyId);
            createdObjects.put(MapKeys.SUBJECT_ID_1, subjectId1);
            createdObjects.put(MapKeys.MARK_1, markValue1);
            createdObjects.put(MapKeys.SUBJECT_ID_2, subjectId2);
            createdObjects.put(MapKeys.MARK_2, markValue2);
            createdObjects.put(MapKeys.SUBJECT_ID_3, subjectId3);
            createdObjects.put(MapKeys.MARK_3, markValue3);
            createdObjects.put(MapKeys.SUBJECT_ID_4, subjectId4);
            createdObjects.put(MapKeys.MARK_4, markValue4);
            createdObjects.put(MapKeys.PERSONAL_NUMBER, parameters.get(MapKeys.PERSONAL_NUMBER));
            createdObjects.put(MapKeys.PASSPORT_SERIES_AND_NUMBER,
                    parameters.get(MapKeys.PASSPORT_SERIES_AND_NUMBER));
            return enrolleeDao.add(createdObjects);
        } catch (DaoException | NoSuchAlgorithmException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<Passport> findEnrolleePassport(int enrolleeId) throws ServiceException {
        EnrolleeDaoImpl enrolleeDao = EnrolleeDaoImpl.getInstance();
        try {
            Optional<Passport> passport = enrolleeDao.findPassportByEnrolleeId(enrolleeId);
            return passport;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<Enrollee> updateEnrolleeNameInformation(Enrollee enrollee, Map<String, String> parameters)
            throws ServiceException {
        EnrolleeDaoImpl enrolleeDao = EnrolleeDaoImpl.getInstance();
        enrollee.setFirstName(parameters.get(MapKeys.FIRST_NAME));
        enrollee.setLastName(parameters.get(MapKeys.LAST_NAME));
        enrollee.setMiddleName(parameters.get(MapKeys.MIDDLE_NAME));
        try {
            if (enrolleeDao.updateEnrollee(enrollee)) {
                return Optional.of(enrollee);
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<Passport> updateEnrolleePassportInformation(Passport passport, Map<String, String> parameters)
            throws ServiceException {
        EnrolleeDaoImpl enrolleeDao = EnrolleeDaoImpl.getInstance();
        passport.setPassportSeriesAndNumber(parameters.get(MapKeys.PASSPORT_SERIES_AND_NUMBER));
        passport.setPersonalNumber(parameters.get(MapKeys.PERSONAL_NUMBER));
        try {
            if (enrolleeDao.updatePassport(passport)) {
                return Optional.of(passport);
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<Enrollee> updateEnrolleeSpecialty(Enrollee enrollee, String specialtyId)
            throws ServiceException {
        EnrolleeDaoImpl enrolleeDao = EnrolleeDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        int intSpecialtyId = parser.parseToInt(specialtyId);
        enrollee.setChosenSpecialtyId(intSpecialtyId);
        try {
            if (enrolleeDao.updateEnrolleeSpecialty(enrollee)) {
                return Optional.of(enrollee);
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}