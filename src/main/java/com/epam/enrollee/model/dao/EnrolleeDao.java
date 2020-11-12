package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Passport;

import java.util.List;
import java.util.Optional;

public interface EnrolleeDao extends BaseDao<Enrollee> {

    boolean updateEnrollee(Enrollee enrollee) throws DaoException;

    boolean updatePassport(Passport passport) throws DaoException;

    boolean updateEnrolleeSpecialty(Enrollee enrollee) throws DaoException;

    Optional<Enrollee> findEnrolleeByEmail(String email) throws DaoException;

    Optional<Passport> findPassportByEnrolleeId(int enrolleeId) throws DaoException;

    List<Enrollee> findEnrolleeBySpecialtyId(int specialtyId) throws DaoException;

    boolean updateApplicationStatusByEnrolleeId(int enrolleeId, String applicationStatus) throws DaoException;
}
