package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.SubjectDaoImpl;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SubjectServiceImpl implements BaseService<Subject> {
    @Override
    public boolean create(Map<String, String> parameters) throws ServiceException {
        return false;
    }

    @Override
    public int remove(Subject user) throws ServiceException {
        return 0;
    }

    @Override
    public Optional<Subject> find(String value) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public List<Subject> findAll() throws ServiceException {
        SubjectDaoImpl dao = SubjectDaoImpl.getInstance();
        try {
            List<Subject> subjects = dao.findAll();
                return subjects;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean update(int value, Map<String, String> parameters) throws ServiceException {
        return false;
    }
}
