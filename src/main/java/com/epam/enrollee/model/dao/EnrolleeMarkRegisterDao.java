package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Subject;

import java.util.List;
import java.util.Optional;

public interface EnrolleeMarkRegisterDao {

    boolean update(EnrolleeMarkRegister register, int enrolleeId) throws DaoException;

    boolean update(EnrolleeMarkRegister register, int enrolleeId, List<Subject> subjectsForUpdate) throws DaoException;

    Optional<EnrolleeMarkRegister> findEnrolleeMarkRegisterByEnrolleeId(int enrolleeId) throws DaoException;

}
