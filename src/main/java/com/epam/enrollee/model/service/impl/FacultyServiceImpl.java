package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.FacultyDaoImpl;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.service.BaseService;
import com.epam.enrollee.model.type.StatusType;
import com.epam.enrollee.util.MapKeys;
import com.epam.enrollee.validator.ProjectValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class FacultyServiceImpl implements BaseService<Faculty> {

    private static Logger logger = LogManager.getLogger();
    private static final String EMPTY_STRING = "";

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
            throw new ServiceException("Impossible add faculty", e);
        }
    }

    @Override
    public int remove(Faculty faculty) throws ServiceException {
        return 0;
    }

    public boolean remove(int facultyId) throws ServiceException {
        FacultyDaoImpl facultyDao = FacultyDaoImpl.getInstance();
        try {
            return facultyDao.UpdateStatusById(facultyId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean checkFacultyAplications(int facultyId) throws ServiceException {
        FacultyDaoImpl facultyDao = FacultyDaoImpl.getInstance();
        try {
            List<Integer> foundEnrolleId = facultyDao.findEnrolleeIdByFacultyId(facultyId);
            return foundEnrolleId.size() > 0;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<Faculty> find(String value) throws ServiceException {
        return Optional.empty();
    }

    //login admin
    @Override
    public List<Faculty> findAll() throws ServiceException {
        FacultyDaoImpl dao = FacultyDaoImpl.getInstance();
        try {
            List<Faculty> faculties = dao.findAll();
            return faculties;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(int facultyId, Map<String, String> parameters) throws ServiceException {
        FacultyDaoImpl facultyDao = FacultyDaoImpl.getInstance();
        Faculty faculty = new Faculty();
        faculty.setFacultyId(facultyId);
        faculty.setFacultyName(parameters.get(MapKeys.FACULTY_NAME));
        faculty.setFacultyDescription(parameters.get(MapKeys.FACULTY_DESCRIPTION));
        faculty.setFacultyStatus(StatusType.ACTIVE);
        try {
            return facultyDao.update(faculty);
        } catch (DaoException e) {
            throw new ServiceException("Impossible add faculty", e);
        }
    }

    public Optional<Faculty> findFacultyById(int faultyId) throws ServiceException {
        FacultyDaoImpl dao = FacultyDaoImpl.getInstance();
        try {
            Optional<Faculty> faculty = dao.findById(faultyId);
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
        isNameValid = validator.isStringParameterValid(parameters.get(MapKeys.FACULTY_NAME));
        String description = validator.validateDescription(parameters.get(MapKeys.FACULTY_DESCRIPTION));
        if (!isNameValid) {
            parameters.put(MapKeys.FACULTY_NAME, EMPTY_STRING);
            parameters.put(MapKeys.FACULTY_DESCRIPTION, description);
        }
        return parameters;
    }
}
