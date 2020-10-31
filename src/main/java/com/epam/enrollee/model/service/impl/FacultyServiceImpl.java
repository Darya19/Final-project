package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.FacultyDaoImpl;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FacultyServiceImpl implements BaseService<Faculty> {

    @Override
    public Optional<Faculty> create(Map<String, String> parameters) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public int remove(Faculty user) throws ServiceException {
        return 0;
    }

    @Override
    public Optional<Faculty> find(String value) throws ServiceException {
        return Optional.empty();
    }

    public Optional<Faculty> findFacultyByUserId(int userId) throws ServiceException {
        FacultyDaoImpl dao = FacultyDaoImpl.getInstance();
        try {
            Optional<Faculty> foundFaculty = dao.findFacultyByUserId(userId);
            return foundFaculty;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<List<Faculty>> findAll() throws ServiceException {
        FacultyDaoImpl dao = FacultyDaoImpl.getInstance();
        try {
            Optional<List<Faculty>> faculties = dao.findAll();
            if (faculties.isPresent()) {
                return faculties;
            } else return Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<List<Faculty>> update(Faculty value) throws ServiceException {
        return null;
    }

    public Optional<Faculty> findEnrolleeFaculty(int faultyId) throws ServiceException {
        FacultyDaoImpl dao = FacultyDaoImpl.getInstance();
        try {
            Optional<Faculty> faculty = dao.findById(faultyId);
            return faculty;
        } catch (DaoException e) {
           throw new ServiceException(e);
        }
    }

}
