package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.EnrolleeMarkRegisterDaoImpl;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.service.BaseService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EnrolleeMarkRegisterServiceImpl implements BaseService<EnrolleeMarkRegister> {

    @Override
    public Optional<EnrolleeMarkRegister> create(Map<String, String> parameters) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public int remove(EnrolleeMarkRegister value) throws ServiceException {
        return 0;
    }

    @Override
    public Optional<EnrolleeMarkRegister> find(String value) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public Optional<List<EnrolleeMarkRegister>> findAll() throws ServiceException {
        return Optional.empty();
    }

    @Override
    public Optional<List<EnrolleeMarkRegister>> update(EnrolleeMarkRegister value) throws ServiceException {
        return null;
    }

    public Optional<EnrolleeMarkRegister> findEnrolleeMarkRegister(int enrolleeId) throws ServiceException {
        EnrolleeMarkRegisterDaoImpl dao = EnrolleeMarkRegisterDaoImpl.getInstance();
        try {
            Optional<EnrolleeMarkRegister> enrolleeRegister = dao.findEnrolleeMarkRegisterByEnrolleeId(enrolleeId);
                return enrolleeRegister;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
