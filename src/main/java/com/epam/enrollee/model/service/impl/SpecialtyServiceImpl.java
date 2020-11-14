package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.SpecialtyDaoImpl;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.SpecialtyService;
import com.epam.enrollee.model.type.RecruitmentType;
import com.epam.enrollee.parser.NumberParser;
import com.epam.enrollee.util.MapKeys;
import com.epam.enrollee.validator.ProjectValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class SpecialtyServiceImpl implements SpecialtyService {

    public static SpecialtyServiceImpl instance;
    private static Logger logger = LogManager.getLogger();

    public static SpecialtyServiceImpl getInstance() {
        if (instance == null) {
            instance = new SpecialtyServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean create(Map<String, String> parameters) throws ServiceException {
        SpecialtyDaoImpl specialtyDao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        Map<String, Object> objectMap = new HashMap<>();
        int numberOfSeats = parser.parseToInt(parameters.get(MapKeys.NUMBER_OF_SEATS));
        int facultyId = parser.parseToInt(parameters.get(MapKeys.FACULTY_ID));
        objectMap.put(MapKeys.NUMBER_OF_SEATS, numberOfSeats);
        objectMap.put(MapKeys.SPECIALTY_NAME, parameters.get(MapKeys.SPECIALTY_NAME));
        objectMap.put(MapKeys.FACULTY_ID, facultyId);
        try {
            return specialtyDao.add(objectMap);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in adding specialty", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean remove(String specialtyId) throws ServiceException {
        SpecialtyDaoImpl specialtyDao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        int intSpecialtyId;
        if (validator.isIntParameterValid(specialtyId)) {
            try {
                intSpecialtyId = parser.parseToInt(specialtyId);
                return specialtyDao.updateStatusById(intSpecialtyId);
            } catch (DaoException e) {
                logger.log(Level.ERROR, "Error in removing specialty", e);
                throw new ServiceException(e);
            }
        } else {
            return false;
        }
    }

    @Override
    public List<Specialty> findAllOpenSpecialties() throws ServiceException {
        SpecialtyDaoImpl dao = SpecialtyDaoImpl.getInstance();
        try {
            List<Specialty> specialties = dao.findAll();
            return specialties;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in finding open specialties", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Specialty> findEnrolleeSpecialty(int enrolleeId) throws ServiceException {
        SpecialtyDaoImpl dao = SpecialtyDaoImpl.getInstance();
        try {
            Optional<Specialty> specialty = dao.findByEnrolleeId(enrolleeId);
            return specialty;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in finding enrollee specialty", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(Map<String, String> parameters) throws ServiceException {
        SpecialtyDaoImpl specialtyDao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        Map<String, Object> objectParameters = new HashMap<>();
        int intSpecialtyId;
        int number;
        try {
            intSpecialtyId = parser.parseToInt(parameters.get(MapKeys.SPECIALTY_ID));
            number = parser.parseToInt(parameters.get(MapKeys.NUMBER_OF_SEATS));
            objectParameters.put(MapKeys.SPECIALTY_ID, intSpecialtyId);
            objectParameters.put(MapKeys.SPECIALTY_NAME, parameters.get(MapKeys.SPECIALTY_NAME));
            objectParameters.put(MapKeys.NUMBER_OF_SEATS, number);
            return specialtyDao.update(objectParameters);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in updating specialty", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Specialty> findOpenSpecialtiesOfFaculty(String facultyId) throws ServiceException {
        SpecialtyDaoImpl dao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        List<Specialty> specialties;
        int intFacultyId;
        if (validator.isIntParameterValid(facultyId)) {
            intFacultyId = parser.parseToInt(facultyId);
            try {
                specialties = dao.findOpenSpecialtiesListByFacultyId(intFacultyId);
                return specialties;
            } catch (DaoException e) {
                logger.log(Level.ERROR, "Error in finding open specialties of faculty", e);
                throw new ServiceException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Specialty> findActiveSpecialtiesOfFaculty(String facultyId) throws ServiceException {
        SpecialtyDaoImpl dao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        List<Specialty> specialties = null;
        int intFacultyId;
        if (validator.isIntParameterValid(facultyId)) {
            intFacultyId = parser.parseToInt(facultyId);
            try {
                specialties = dao.findActiveSpecialtiesListByFacultyId(intFacultyId);
                return specialties;
            } catch (DaoException e) {
                logger.log(Level.ERROR, "Error in finding active specialties of faculty", e);
                throw new ServiceException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Specialty> findSpecialtyById(String specialtyId) throws ServiceException {
        SpecialtyDaoImpl specialtyDao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        int intSpecialtyId;
        if (validator.isIntParameterValid(specialtyId)) {
            intSpecialtyId = parser.parseToInt(specialtyId);
            try {
                Optional<Specialty> specialty = specialtyDao.findById(intSpecialtyId);
                return specialty;
            } catch (DaoException e) {
                logger.log(Level.ERROR, "Error in finding specialty by id", e);
                throw new ServiceException(e);
            }
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean changeSpecialtyRecruitment(String specialtyId, String recruitment, List<Integer> application)
            throws ServiceException {
        SpecialtyDaoImpl specialtyDao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        int intSpecialtyId;
        boolean isChanged;
        if (validator.isIntParameterValid(specialtyId)
                && validator.isStringParameterValid(recruitment)) {
            try {
                intSpecialtyId = parser.parseToInt(specialtyId);
                if (recruitment.equalsIgnoreCase(RecruitmentType.OPENED.getType())) {
                    isChanged = specialtyDao.updateOpenedRecruitmentBySpecialtyId(intSpecialtyId, application);
                } else {
                    isChanged = specialtyDao.updateClosedRecruitmentBySpecialtyId(intSpecialtyId);
                }
                return isChanged;
            } catch (DaoException e) {
                logger.log(Level.ERROR, "Error in changing specialty recruitment", e);
                throw new ServiceException(e);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean checkConsideredApplications(String specialtyId) throws ServiceException {
        SpecialtyDaoImpl specialtyDao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        int intSpecialtyId;
        if (validator.isIntParameterValid(specialtyId)) {
            try {
                intSpecialtyId = parser.parseToInt(specialtyId);
                List<Integer> foundEnrolleeId = specialtyDao.findConsideredEnrolleeIdById(intSpecialtyId);
                return foundEnrolleeId.size() > 0;
            } catch (DaoException e) {
                logger.log(Level.ERROR, "Error in checking applications", e);
                throw new ServiceException(e);
            }
        } else {
            return false;
        }
    }

    public List<Integer> findAllApplicationsBySpecialty(String specialtyId) throws ServiceException {
        SpecialtyDaoImpl specialtyDao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        int intSpecialtyId;
        if (validator.isIntParameterValid(specialtyId)) {
            try {
                intSpecialtyId = parser.parseToInt(specialtyId);
                List<Integer> foundEnrolleeId = specialtyDao.findAllUnarchivedEnrolleeBySpecialtyId(intSpecialtyId);
                return foundEnrolleeId;
            } catch (DaoException e) {
                logger.log(Level.ERROR, "Error in finding all applications", e);
                throw new ServiceException(e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Map<String, String> checkParameters(Map<String, String> parameters) {
        ProjectValidator validator = new ProjectValidator();
        boolean isNameValid;
        boolean isNumberValid;
        if (parameters.get(MapKeys.SPECIALTY_ID) != null) {
            boolean isSpecialtyIdValid;
            isSpecialtyIdValid = validator.isIntParameterValid(parameters.get(MapKeys.SPECIALTY_ID));
            if (!isSpecialtyIdValid) {
                parameters.put(MapKeys.SPECIALTY_ID, EMPTY_VALUE);
            }
        }
        isNameValid = validator.isStringParameterValid(parameters.get(MapKeys.SPECIALTY_NAME));
        isNumberValid = validator.isIntParameterValid(parameters.get(MapKeys.NUMBER_OF_SEATS));
        if (!isNameValid) {
            parameters.put(MapKeys.SPECIALTY_NAME, EMPTY_VALUE);
        }
        if (!isNumberValid) {
            parameters.put(MapKeys.NUMBER_OF_SEATS, EMPTY_VALUE);
        }
        return parameters;
    }
}
