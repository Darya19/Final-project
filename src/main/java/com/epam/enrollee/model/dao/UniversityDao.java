package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UniversityDao<T> extends BaseDao<T> {

    boolean updateStatusById(int facultyId) throws DaoException;

    boolean update(Map<String, Object> parameters) throws DaoException;

    Optional<T> findByEnrolleeId(int enrolleeId) throws DaoException;

    List<Integer> findConsideredEnrolleeIdById(int facultyId) throws DaoException;

}
