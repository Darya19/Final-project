package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Passport;
import com.epam.enrollee.model.entity.Specialty;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EnrolleeService extends BaseService {

    Optional<Enrollee> findEnrolleeByEmail(String email) throws ServiceException;

    Optional<Passport> findEnrolleePassport(int enrolleeId) throws ServiceException;

    Optional<Enrollee> updateEnrolleeNameInformation(Enrollee enrollee, Map<String, String> parameters)
            throws ServiceException;

    Optional<Passport> updateEnrolleePassportInformation(Passport passport, Map<String, String> parameters)
            throws ServiceException;

    Optional<Enrollee> updateEnrolleeSpecialty(Enrollee enrollee, String specialtyId) throws ServiceException;

    boolean remove(Map<String, Object> parameters) throws ServiceException;

    List<Enrollee> findAllUnarchivedEnrolleesOnSpecialty(String specialtyId) throws ServiceException;

    boolean changeApplicationStatus(String enrolleeId, String status, Specialty specialty) throws ServiceException;
}
