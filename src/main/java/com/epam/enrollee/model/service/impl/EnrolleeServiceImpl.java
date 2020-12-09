package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.*;
import com.epam.enrollee.model.dao.impl.*;
import com.epam.enrollee.model.entity.*;
import com.epam.enrollee.model.service.EnrolleeService;
import com.epam.enrollee.model.type.ApplicationStatus;
import com.epam.enrollee.model.type.RoleType;
import com.epam.enrollee.model.type.StatusType;
import com.epam.enrollee.util.MapKeys;
import com.epam.enrollee.util.NumberParser;
import com.epam.enrollee.util.PasswordEncryptor;
import com.epam.enrollee.validator.ProjectValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * The type Enrollee service.
 * Class implements base service and enrollee service.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class EnrolleeServiceImpl implements EnrolleeService {

    /**
     * The constant instance.
     */
    public static EnrolleeServiceImpl instance;
    private static Logger logger = LogManager.getLogger();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static EnrolleeServiceImpl getInstance() {
        if (instance == null) {
            instance = new EnrolleeServiceImpl();
        }
        return instance;
    }

    @Override
    public Optional<Enrollee> findEnrolleeByEmail(String email) throws ServiceException {
        EnrolleeDao enrolleeDao = EnrolleeDaoImpl.getInstance();
        try {
            Optional<Enrollee> foundEnrollee = enrolleeDao.findEnrolleeByEmail(email);
            return foundEnrollee;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while checking enrollee email", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, String> checkParameters(Map<String, String> parameters) throws ServiceException {
        ProjectValidator validator = ProjectValidator.getInstance();
        UserDao userDao = UserDaoImpl.getInstance();
        SpecialtyDao specialtyDao = SpecialtyDaoImpl.getInstance();
        SubjectDao subjectDao = SubjectDaoImpl.getInstance();
        NumberParser parser = NumberParser.getInstance();
        parameters = validator.validateRegistrationData(parameters);
        int foundFacultyId;
        try {
            if (parameters.containsKey(MapKeys.EMAIL) && !parameters.get(MapKeys.EMAIL).isEmpty()) {
                Optional<User> user = userDao.findUserByEmail(parameters.get(MapKeys.EMAIL));
                if (user.isPresent()) {
                    parameters.put(MapKeys.EMAIL, EMPTY_VALUE);
                }
            }
            if (parameters.containsKey(MapKeys.PASSWORD)
                    && (parameters.containsKey(MapKeys.REPEATED_PASSWORD)
                    && !parameters.get(MapKeys.PASSWORD).isEmpty()
                    && !parameters.get(MapKeys.REPEATED_PASSWORD).isEmpty())) {
                if (!parameters.get(MapKeys.PASSWORD).equals(parameters.get(MapKeys.REPEATED_PASSWORD))) {
                    parameters.put(MapKeys.REPEATED_PASSWORD, EMPTY_VALUE);
                    parameters.put(MapKeys.PASSWORD, EMPTY_VALUE);
                }
            }
            if (parameters.containsKey(MapKeys.SPECIALTY_ID)
                    && !parameters.get(MapKeys.SPECIALTY_ID).isEmpty()) {
                int intSpecialtyId = parser.parseToInt(parameters.get(MapKeys.SPECIALTY_ID));
                Optional<Integer> optionalFacultyId = specialtyDao.findFacultyIdBySpecialtyId(intSpecialtyId);
                List<Subject> subjects = subjectDao
                        .findSubjectsBySpecialtyId(intSpecialtyId);
                if (optionalFacultyId.isPresent()) {
                    foundFacultyId = optionalFacultyId.get();
                    parameters.put(MapKeys.FACULTY_ID, String.valueOf(foundFacultyId));
                } else {
                    parameters.put(MapKeys.FACULTY_ID, EMPTY_VALUE);
                }
                if (!subjects.isEmpty()) {
                    Set<String> subjectsId = new HashSet<>();
                    subjectsId.add(parameters.get(MapKeys.SUBJECT_ID_1));
                    subjectsId.add(parameters.get(MapKeys.SUBJECT_ID_2));
                    subjectsId.add(parameters.get(MapKeys.SUBJECT_ID_3));
                    subjectsId.add(parameters.get(MapKeys.SUBJECT_ID_4));

                    if (subjectsId.size() == 4) {
                        for (Subject subject : subjects) {
                            int foundSubjectId = subject.getSubjectId();
                            if (!subjectsId.contains(String.valueOf(foundSubjectId))
                                    && foundSubjectId != 2 && foundSubjectId != 3) {
                                parameters.put(MapKeys.SUBJECT_ID_1, EMPTY_VALUE);
                                parameters.put(MapKeys.SUBJECT_ID_2, EMPTY_VALUE);
                                parameters.put(MapKeys.SUBJECT_ID_3, EMPTY_VALUE);
                                parameters.put(MapKeys.SUBJECT_ID_4, EMPTY_VALUE);
                            }
                        }
                    } else {
                        parameters.put(MapKeys.SUBJECT_ID_1, EMPTY_VALUE);
                        parameters.put(MapKeys.SUBJECT_ID_2, EMPTY_VALUE);
                        parameters.put(MapKeys.SUBJECT_ID_3, EMPTY_VALUE);
                        parameters.put(MapKeys.SUBJECT_ID_4, EMPTY_VALUE);
                    }
                } else {
                    parameters.put(MapKeys.SUBJECT_ID_1, EMPTY_VALUE);
                    parameters.put(MapKeys.SUBJECT_ID_2, EMPTY_VALUE);
                    parameters.put(MapKeys.SUBJECT_ID_3, EMPTY_VALUE);
                    parameters.put(MapKeys.SUBJECT_ID_4, EMPTY_VALUE);
                }
            }
            return parameters;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in checking parameters", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean create(Map<String, String> parameters) throws ServiceException {
        EnrolleeDao enrolleeDao = EnrolleeDaoImpl.getInstance();
        NumberParser parser = NumberParser.getInstance();
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
            logger.log(Level.ERROR, "Error while adding enrollee to db", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Passport> findEnrolleePassport(int enrolleeId) throws ServiceException {
        EnrolleeDao enrolleeDao = EnrolleeDaoImpl.getInstance();
        try {
            Optional<Passport> passport = enrolleeDao.findPassportByEnrolleeId(enrolleeId);
            return passport;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error while searching passport", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Enrollee> updateEnrolleeNameInformation(Enrollee enrollee, Map<String, String> parameters)
            throws ServiceException {
        EnrolleeDao enrolleeDao = EnrolleeDaoImpl.getInstance();
        enrollee.setFirstName(parameters.get(MapKeys.FIRST_NAME));
        enrollee.setLastName(parameters.get(MapKeys.LAST_NAME));
        enrollee.setMiddleName(parameters.get(MapKeys.MIDDLE_NAME));
        try {
            return enrolleeDao.updateEnrollee(enrollee) ? Optional.of(enrollee) : Optional.empty();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in updating enrollee information", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Passport> updateEnrolleePassportInformation(Passport passport, Map<String, String> parameters)
            throws ServiceException {
        EnrolleeDao enrolleeDao = EnrolleeDaoImpl.getInstance();
        passport.setPassportSeriesAndNumber(parameters.get(MapKeys.PASSPORT_SERIES_AND_NUMBER));
        passport.setPersonalNumber(parameters.get(MapKeys.PERSONAL_NUMBER));
        try {
            return enrolleeDao.updatePassport(passport) ? Optional.of(passport) : Optional.empty();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in updating passport information", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateEnrolleeFaculty(Enrollee enrollee, String facultyId) throws ServiceException {
        EnrolleeDao enrolleeDao = EnrolleeDaoImpl.getInstance();
        NumberParser parser = NumberParser.getInstance();
        ProjectValidator validator = ProjectValidator.getInstance();
        boolean isUpdated = false;
        try {
            if (validator.isIntParameterValid(facultyId)) {
                int intFacultyId = parser.parseToInt(facultyId);
                enrollee.setChosenFacultyId(intFacultyId);
                isUpdated = enrolleeDao.updateEnrolleeFaculty(enrollee);
            }
            return isUpdated;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in updating enrollee specialty", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateEnrolleeSpecialty(Enrollee enrollee, String specialtyId) throws ServiceException {
        EnrolleeDao enrolleeDao = EnrolleeDaoImpl.getInstance();
        NumberParser parser = NumberParser.getInstance();
        ProjectValidator validator = ProjectValidator.getInstance();
        boolean isUpdated = false;
        try {
            if (validator.isIntParameterValid(specialtyId)) {
                int intSpecialtyId = parser.parseToInt(specialtyId);
                enrollee.setChosenSpecialtyId(intSpecialtyId);
                isUpdated = enrolleeDao.updateEnrolleeSpecialty(enrollee);
            }
            return isUpdated;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in updating enrollee specialty", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean remove(Map<String, Object> parameters) throws ServiceException {
        EnrolleeDao enrolleeDao = EnrolleeDaoImpl.getInstance();
        try {
            return enrolleeDao.remove(parameters);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in removing enrollee specialty", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Enrollee> findAllUnarchivedEnrolleesOnSpecialty(String specialtyId) throws ServiceException {
        EnrolleeDao enrolleeDao = EnrolleeDaoImpl.getInstance();
        NumberParser parser = NumberParser.getInstance();
        ProjectValidator validator = ProjectValidator.getInstance();
        List<Enrollee> enrollees = new ArrayList<>();
        int intSpecialtyId;
        try {
            if (validator.isIntParameterValid(specialtyId)) {
                intSpecialtyId = parser.parseToInt(specialtyId);
                enrollees = enrolleeDao.findUnarchivedEnrolleeBySpecialtyId(intSpecialtyId);
            }
            return enrollees;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in finding enrollees by current specialty", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean changeApplicationStatus(String enrolleeId, String status, Specialty specialty) throws ServiceException {
        EnrolleeDao enrolleeDao = EnrolleeDaoImpl.getInstance();
        SpecialtyDao specialtyDao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = NumberParser.getInstance();
        ProjectValidator validator = ProjectValidator.getInstance();
        int intEnrolleeId;
        boolean isChanged = false;
        try {
            if (validator.isIntParameterValid(enrolleeId)
                    && validator.isStringParameterValid(status)) {
                intEnrolleeId = parser.parseToInt(enrolleeId);
                if (status.equals(ApplicationStatus.ACCEPTED.getApplicationStatus())) {
                    int count = specialtyDao.findAllEnrolleeWithAcceptedApplicationStatus(specialty.getSpecialtyId());
                    if (count < specialty.getNumberOfSeats()) {
                        isChanged = enrolleeDao.updateApplicationStatusByEnrolleeId(intEnrolleeId,
                                ApplicationStatus.ACCEPTED.getApplicationStatus());
                    }
                } else {
                    isChanged = enrolleeDao.updateApplicationStatusByEnrolleeId(intEnrolleeId,
                            ApplicationStatus.REJECTED.getApplicationStatus());
                }
            }
            return isChanged;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in changing application status", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<EnrolleeMarkRegister> updateEnrolleeApplication(Enrollee enrollee, Map<String, String> parameters)
            throws ServiceException {
        EnrolleeMarkRegisterDao registerDao = EnrolleeMarkRegisterDaoImpl.getInstance();
        EnrolleeDao enrolleeDao = EnrolleeDaoImpl.getInstance();
        SubjectDao subjectDao = SubjectDaoImpl.getInstance();
        NumberParser parser = NumberParser.getInstance();
        EnrolleeMarkRegister currentRegister = new EnrolleeMarkRegister();
        int specialtyId = parser.parseToInt(parameters.get(MapKeys.SPECIALTY_ID));
        int facultyId = parser.parseToInt(parameters.get(MapKeys.FACULTY_ID));
        int subjectId1 = parser.parseToInt(parameters.get(MapKeys.SUBJECT_ID_1));
        int subjectId2 = parser.parseToInt(parameters.get(MapKeys.SUBJECT_ID_2));
        int subjectId3 = parser.parseToInt(parameters.get(MapKeys.SUBJECT_ID_3));
        int subjectId4 = parser.parseToInt(parameters.get(MapKeys.SUBJECT_ID_4));
        int markValue1 = parser.parseToInt(parameters.get(MapKeys.MARK_1));
        int markValue2 = parser.parseToInt(parameters.get(MapKeys.MARK_2));
        int markValue3 = parser.parseToInt(parameters.get(MapKeys.MARK_3));
        int markValue4 = parser.parseToInt(parameters.get(MapKeys.MARK_4));
        try {
            Optional<Subject> subject = subjectDao.findById(subjectId1);
            subject.ifPresent(s -> currentRegister.put(s, markValue1));
            Optional<Subject> subject2 = subjectDao.findById(subjectId2);
            subject2.ifPresent(s -> currentRegister.put(s, markValue2));
            Optional<Subject> subject3 = subjectDao.findById(subjectId3);
            subject3.ifPresent(s -> currentRegister.put(s, markValue3));
            Optional<Subject> subject4 = subjectDao.findById(subjectId4);
            subject4.ifPresent(s -> currentRegister.put(s, markValue4));
            enrollee.setChosenSpecialtyId(specialtyId);
            enrollee.setChosenFacultyId(facultyId);
            enrollee.setApplicationStatus(ApplicationStatus.CONSIDERED);
            return enrolleeDao.updateEnrolleeApplication(enrollee, currentRegister) ? Optional.of(currentRegister)
                    : Optional.empty();
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in updating register", e);
            throw new ServiceException(e);
        }
    }
}