package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;

import java.util.Optional;

public interface EnrolleeMarkRegisterDao {

    boolean update(EnrolleeMarkRegister register, int enrolleeId) throws DaoException;

    Optional<EnrolleeMarkRegister> findEnrolleeMarkRegisterByEnrolleeId(int enrolleeId) throws DaoException;
}
