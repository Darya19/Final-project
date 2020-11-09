package com.epam.enrollee.controller.command;

import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.*;
import com.epam.enrollee.model.service.impl.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public interface Command {

    Router execute(HttpServletRequest request);

    default int putInSessionEnrolleeAndPassportAndMarks(String email, HttpSession session)
            throws ServiceException {
        EnrolleeServiceImpl enrolleeService = new EnrolleeServiceImpl();
        EnrolleeMarkRegisterServiceImpl markRegisterService = new EnrolleeMarkRegisterServiceImpl();
        Enrollee enrollee;
        int enrolleeId;
        Optional<Enrollee> optionalEnrollee = enrolleeService.findEnrolleeByEmail(email);
        if (optionalEnrollee.isPresent()) {
            enrollee = optionalEnrollee.get();
            enrolleeId = enrollee.getUserId();
            Optional<EnrolleeMarkRegister> optionalEnrolleeRegister = markRegisterService
                    .findEnrolleeMarkRegister(enrolleeId);
            Optional<Passport> optionalPassport = enrolleeService.findEnrolleePassport
                    (enrolleeId);
            if (optionalEnrolleeRegister.isPresent() && optionalPassport.isPresent()) {
                EnrolleeMarkRegister register = markRegisterService.calculateEnrolleeAverageMark
                        (optionalEnrolleeRegister.get());
                session.setAttribute(RequestParameters.ENROLLEE, enrollee);
                session.setAttribute(RequestParameters.REGISTER, register);
                session.setAttribute(RequestParameters.PASSPORT, optionalPassport.get());
            } else {
                enrolleeId = -1;
            }
        } else {
            enrolleeId = -1;
        }
        return enrolleeId;
    }

    default boolean putEnrolleeFacultyAndSpecialtyInSession(int enrolleeId, HttpSession session)
            throws ServiceException {
        FacultyServiceImpl facultyService = new FacultyServiceImpl();
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        boolean isAdded;
        Optional<Faculty> optionalFaculty = facultyService.findEnrolleeFaculty(enrolleeId);
        Optional<Specialty> optionalSpecialty = specialtyService.findEnrolleeSpecialty(enrolleeId);
        if (optionalFaculty.isPresent() && optionalSpecialty.isPresent()) {
            session.setAttribute(RequestParameters.FACULTY, optionalFaculty.get());
            session.setAttribute(RequestParameters.SPECIALTY, optionalSpecialty.get());
            isAdded = true;
        } else {
            isAdded = false;
        }
        return isAdded;
    }

    default boolean putFacultiesSpecialtiesSubjectsInRequest(HttpServletRequest request)
            throws ServiceException {
        FacultyServiceImpl facultyService = new FacultyServiceImpl();
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        SubjectServiceImpl subjectService = new SubjectServiceImpl();
        Boolean isAdded;
        List<Faculty> faculties = facultyService.findAll();
        List<Specialty> specialties = specialtyService.findAll();
        List<Subject> subjects = subjectService.findAll();
        if (!faculties.isEmpty() && !specialties.isEmpty() && !subjects.isEmpty()) {
            request.setAttribute(RequestParameters.FACULTIES, faculties);
            request.setAttribute(RequestParameters.SPECIALTIES, specialties);
            request.setAttribute(RequestParameters.SUBJECTS, subjects);
            isAdded = true;
        } else {
            isAdded = false;
        }
        return isAdded;
    }
}
