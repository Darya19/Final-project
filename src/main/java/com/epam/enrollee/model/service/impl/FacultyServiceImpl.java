package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.FacultyDaoImpl;
import com.epam.enrollee.model.dao.impl.SpecialtyDaoImpl;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.parser.NumberParser;
import com.epam.enrollee.util.MapKeys;
import com.epam.enrollee.validator.ProjectValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FacultyServiceImpl {

    private static Logger logger = LogManager.getLogger();
    private static final String EMPTY_STRING = "";

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
            throw new ServiceException("Impossible add faculty", e);
        }
    }

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
                throw new ServiceException(e);
            }
        } else {
            return false;
        }
    }

    public boolean checkApplications(String facultyId) throws ServiceException {
        FacultyDaoImpl facultyDao = FacultyDaoImpl.getInstance();
        NumberParser parser = new NumberParser();
        ProjectValidator validator = new ProjectValidator();
        int intFacultyId;
        if (validator.isIntParameterValid(facultyId)) {
            try {
                intFacultyId = parser.parseToInt(facultyId);
                List<Integer> foundEnrolleId = facultyDao.findEnrolleeIdByFacultyId(intFacultyId);
                return foundEnrolleId.size() > 0;
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        } else {
            return false;
        }
    }


    public Optional<Faculty> find(String value) throws ServiceException {
        return Optional.empty();
    }

    //login admin
    public List<Faculty> findAllActiveFaculties() throws ServiceException {
        FacultyDaoImpl dao = FacultyDaoImpl.getInstance();
        try {
            List<Faculty> faculties = dao.findAll();
            return faculties;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


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
            throw new ServiceException("Impossible add faculty", e);
        }
    }

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
            throw new ServiceException(e);
        }
    }

    //login register
    public Optional<Faculty> findEnrolleeFaculty(int enrolleeId) throws ServiceException {
        FacultyDaoImpl dao = FacultyDaoImpl.getInstance();
        try {
            Optional<Faculty> faculty = dao.findFacultyByEnrolleeId(enrolleeId);
            return faculty;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Map<String, String> checkParameters(Map<String, String> parameters) {
        ProjectValidator validator = new ProjectValidator();
        boolean isNameValid;
        if (parameters.get(MapKeys.FACULTY_ID) != null) {
            boolean isFacultyIdValid;
            isFacultyIdValid = validator.isIntParameterValid(parameters.get(MapKeys.FACULTY_ID));
            if (!isFacultyIdValid) {
                parameters.put(MapKeys.FACULTY_ID, EMPTY_STRING);
            }
        }
        isNameValid = validator.isStringParameterValid(parameters.get(MapKeys.FACULTY_NAME));
        String description = validator.validateDescription(parameters.get(MapKeys.FACULTY_DESCRIPTION));
        if (!isNameValid) {
            parameters.put(MapKeys.FACULTY_NAME, EMPTY_STRING);
            parameters.put(MapKeys.FACULTY_DESCRIPTION, description);
        }
        return parameters;
    }
}
