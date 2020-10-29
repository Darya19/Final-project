package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.SpecialtyDaoImpl;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SpecialtyServiceImpl implements BaseService<Specialty> {

    @Override
    public Optional<Specialty> create(Map<String, String> parameters) throws ServiceException {
        return null;
    }

    @Override
    public int remove(Specialty user) throws ServiceException {
        return 0;
    }

    @Override
    public Optional<Specialty> find(String value) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public Optional<List<Specialty>> findAll() throws ServiceException {
        SpecialtyDaoImpl dao = new SpecialtyDaoImpl();
        try {
            Optional<List<Specialty>> specialties = dao.findAll();
            if(specialties.isPresent()){
                return specialties;
            } else return Optional.empty();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Specialty> update(Specialty value) throws ServiceException {
        return null;
    }
}
