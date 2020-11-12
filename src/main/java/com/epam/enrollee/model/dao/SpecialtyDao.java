package com.epam.enrollee.model.dao;

import com.epam.enrollee.exception.DaoException;
import com.epam.enrollee.model.entity.Specialty;

import java.util.List;
import java.util.Optional;

public interface SpecialtyDao extends UniversityDao<Specialty> {

    Optional<Integer> findFacultyIdBySpecialtyId(int specialtyId) throws DaoException;

    List<Specialty> findOpenSpecialtiesListByFacultyId(int facultyId) throws DaoException;

    List<Specialty> findActiveSpecialtiesListByFacultyId(int facultyId) throws DaoException;

    boolean updateRecruitmentBySpecialtyId(int specialtyId, String recruitment) throws DaoException;

    int findAllEnrolleeWithAcceptedApplicationStatus(int specialtyId) throws DaoException;
}
