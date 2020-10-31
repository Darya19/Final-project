package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.FacultyDaoImpl;
import com.epam.enrollee.model.dao.impl.SpecialtyDaoImpl;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Faculty;
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
        SpecialtyDaoImpl dao = SpecialtyDaoImpl.getInstance();
        try {
            Optional<List<Specialty>> specialties = dao.findAll();
                return specialties;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Optional<Specialty> findEnrolleeSpecialty(int specialtyId) throws ServiceException {
       SpecialtyDaoImpl dao = SpecialtyDaoImpl.getInstance();
        try {
            Optional<Specialty> specialty = dao.findById(specialtyId);
            return specialty;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<List<Specialty>> update(Specialty value) throws ServiceException {
        return null;
    }
}
