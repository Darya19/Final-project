package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.entity.Subject;

import java.util.List;

public interface SubjectDao extends BaseDao<Subject> {

    List<Subject> findSubjectsBySpecialtyId(int specialtyId) throws DaoException;
}
