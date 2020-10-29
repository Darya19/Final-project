package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.SubjectDaoImpl;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SubjectServiceImpl implements BaseService<Subject> {
    @Override
    public Optional<Subject> create(Map<String, String> parameters) throws ServiceException {
        return null;
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
    public Optional<List<Subject>> findAll() throws ServiceException {
        SubjectDaoImpl dao = new SubjectDaoImpl();
        try {
            Optional<List<Subject>> subjects = dao.findAll();
            if(subjects.isPresent()){
                return subjects;
            } else return Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Subject> update(Subject value) throws ServiceException {
        return null;
    }
}
