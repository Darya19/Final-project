package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.FacultyDaoImpl;
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
    public List<Faculty> update(Faculty value) throws ServiceException {
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
