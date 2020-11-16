package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;

import java.util.Map;
import java.util.Optional;

public interface EnrolleeMarkRegisterService extends BaseService {

    Optional<EnrolleeMarkRegister> findEnrolleeMarkRegister(int enrolleeId) throws ServiceException;

    Optional<EnrolleeMarkRegister> updateEnrolleeMarks(int enrolleeId, Map<String, String> parameters) throws ServiceException;

    public Optional<EnrolleeMarkRegister> updateEnrolleeRegister(int enrolleId, EnrolleeMarkRegister markRegister,
                                                                 Map<String, String> parameters) throws ServiceException;
}
