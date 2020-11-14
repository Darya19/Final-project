package com.epam.enrollee.model.service;

import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Faculty;

import java.util.Map;
import java.util.Optional;

public interface FacultyService extends BaseService {

    boolean removeFacultyAndItsSpecialties(String facultyId) throws ServiceException;

    boolean checkConsideredApplications(String facultyId) throws ServiceException;

    boolean update(Map<String, String> parameters) throws ServiceException;

    Optional<Faculty> findFacultyById(String facultyId) throws ServiceException;

    Optional<Faculty> findEnrolleeFaculty(int enrolleeId) throws ServiceException;
}
