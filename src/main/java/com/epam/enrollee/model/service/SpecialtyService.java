package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Specialty;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SpecialtyService extends BaseService {

    boolean remove(String specialtyId) throws ServiceException;

    List<Specialty> findAllOpenSpecialties() throws ServiceException;

    Optional<Specialty> findEnrolleeSpecialty(int enrolleeId) throws ServiceException;

    boolean update(Map<String, String> parameters) throws ServiceException;

    List<Specialty> findOpenSpecialtiesOfFaculty(String facultyId) throws ServiceException;

    List<Specialty> findActiveSpecialtiesOfFaculty(String facultyId) throws ServiceException;

    Optional<Specialty> findSpecialtyById(String specialtyId) throws ServiceException;

    boolean changeSpecialtyRecruitment(String specialtyId, String recruitment, List<Integer> application) throws ServiceException;

    boolean checkConsideredApplications(String specialtyId) throws ServiceException;
}
