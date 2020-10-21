package com.epam.enrollee.model.service.impl;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.dao.impl.EnrolleeDaoImpl;
import com.epam.enrollee.model.entity.Enrollee;

import java.util.Optional;

public class EnrolleeServiceImpl {

    public Optional<Enrollee> findEnrolleeByEmail(String email) throws ServiceException {
        EnrolleeDaoImpl dao = new EnrolleeDaoImpl();
        try {
            Optional<Enrollee> foundEnrollee = dao.findEnrolleeByEmail(email);
            return foundEnrollee;
        } catch (DaoException e) {
            throw new ServiceException("");//TODO message
        }
    }
}
