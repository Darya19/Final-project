package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.FacultyDaoImpl;
import com.epam.enrollee.model.dao.impl.SpecialtyDaoImpl;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.FacultyService;
import com.epam.enrollee.parser.NumberParser;
import com.epam.enrollee.util.MapKeys;
import com.epam.enrollee.validator.ProjectValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FacultyServiceImpl implements FacultyService {

    public static FacultyServiceImpl instance;
    private static Logger logger = LogManager.getLogger();

    public static FacultyServiceImpl getInstance() {
        if (instance == null) {
            instance = new FacultyServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean create(Map<String, String> parameters) throws ServiceException {
        FacultyDaoImpl facultyDao = FacultyDaoImpl.getInstance();
        Map<String, Object> objectMap = new HashMap<>();
        for (Map.Entry<String, String> pair : parameters.entrySet()) {
            String key = pair.getKey();
            String value = pair.getValue();
            objectMap.put(key, value);
        }
        try {
            return facultyDao.add(objectMap);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in adding faculty", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean removeFacultyAndItsSpecialties(String facultyId) throws ServiceException {
        FacultyDaoImpl facultyDao = FacultyDaoImpl.getInstance();
        SpecialtyDaoImpl specialtyDao = SpecialtyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        int intFacultyId;
        if (validator.isIntParameterValid(facultyId)) {
            try {
                intFacultyId = parser.parseToInt(facultyId);
                List<Specialty> specialties = specialtyDao.findActiveSpecialtiesListByFacultyId(intFacultyId);
                for (Specialty specialty : specialties) {
                    specialtyDao.updateStatusById(specialty.getSpecialtyId());
                }
                return facultyDao.updateStatusById(intFacultyId);
            } catch (DaoException e) {
                logger.log(Level.ERROR, "Error in removing faculty and it's specialties", e);
                throw new ServiceException(e);
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean checkConsideredApplications(String facultyId) throws ServiceException {
        FacultyDaoImpl facultyDao = FacultyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        int intFacultyId;
        if (validator.isIntParameterValid(facultyId)) {
            try {
                intFacultyId = parser.parseToInt(facultyId);
                List<Integer> foundEnrolleId = facultyDao.findConsideredEnrolleeIdById(intFacultyId);
                return foundEnrolleId.size() > 0;
            } catch (DaoException e) {
                logger.log(Level.ERROR, "Error in checking applications", e);
                throw new ServiceException(e);
            }
        } else {
            return false;
        }
    }

    public List<Faculty> findAllActiveFaculties() throws ServiceException {
        FacultyDaoImpl dao = FacultyDaoImpl.getInstance();
        try {
            List<Faculty> faculties = dao.findAll();
            return faculties;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in finding active faculties", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(Map<String, String> parameters) throws ServiceException {
        FacultyDaoImpl facultyDao = FacultyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        Map<String, Object> objectParameters = new HashMap<>();
        int intFacultyId;
        try {
            intFacultyId = parser.parseToInt(parameters.get(MapKeys.FACULTY_ID));
            objectParameters.put(MapKeys.FACULTY_ID, intFacultyId);
            objectParameters.put(MapKeys.FACULTY_NAME, parameters.get(MapKeys.FACULTY_NAME));
            objectParameters.put(MapKeys.FACULTY_DESCRIPTION, parameters.get(MapKeys.FACULTY_DESCRIPTION));
            return facultyDao.update(objectParameters);
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in updating faculty", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Faculty> findFacultyById(String facultyId) throws ServiceException {
        FacultyDaoImpl facultyDao = FacultyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        int intFacultyId = 0;
        if (validator.isIntParameterValid(facultyId)) {
            intFacultyId = parser.parseToInt(facultyId);
        }
        try {
            Optional<Faculty> faculty = facultyDao.findById(intFacultyId);
            return faculty;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in finding faculty", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Faculty> findEnrolleeFaculty(int enrolleeId) throws ServiceException {
        FacultyDaoImpl dao = FacultyDaoImpl.getInstance();
        try {
            Optional<Faculty> faculty = dao.findByEnrolleeId(enrolleeId);
            return faculty;
        } catch (DaoException e) {
            logger.log(Level.ERROR, "Error in finding enrollee faculty", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Map<String, String> checkParameters(Map<String, String> parameters) {
        ProjectValidator validator = new ProjectValidator();
        boolean isNameValid;
        if (parameters.get(MapKeys.FACULTY_ID) != null) {
            boolean isFacultyIdValid;
            isFacultyIdValid = validator.isIntParameterValid(parameters.get(MapKeys.FACULTY_ID));
            if (!isFacultyIdValid) {
                parameters.put(MapKeys.FACULTY_ID, EMPTY_VALUE);
            }
        }
        isNameValid = validator.isStringParameterValid(parameters.get(MapKeys.FACULTY_NAME));
        String description = validator.validateDescription(parameters.get(MapKeys.FACULTY_DESCRIPTION));
        if (!isNameValid) {
            parameters.put(MapKeys.FACULTY_NAME, EMPTY_VALUE);
            parameters.put(MapKeys.FACULTY_DESCRIPTION, description);
        }
        return parameters;
    }
}
