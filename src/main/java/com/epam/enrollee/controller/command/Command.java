package com.epam.enrollee.controller.command;

import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.*;
import com.epam.enrollee.model.service.*;
import com.epam.enrollee.model.service.impl.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

/**
 * The interface Command.
 */
public interface Command {

    /**
     * The constant EMPTY_VALUE.
     */
    String EMPTY_VALUE = "";

    /**
     * Execute router.
     *
     * @param request the request
     * @return the router
     */
    Router execute(HttpServletRequest request);

    /**
     * Put in session enrollee and passport and marks int.
     *
     * @param email   the email
     * @param session the session
     * @return the int
     * @throws ServiceException the service exception
     */
    default int putInSessionEnrolleeAndPassportAndMarks(String email, HttpSession session) throws ServiceException {
        EnrolleeService enrolleeService = EnrolleeServiceImpl.getInstance();
        EnrolleeMarkRegisterService markRegisterService = EnrolleeMarkRegisterServiceImpl.getInstance();
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
                session.setAttribute(AttributeName.ENROLLEE, enrollee);
                session.setAttribute(AttributeName.REGISTER, optionalEnrolleeRegister.get());
                session.setAttribute(AttributeName.PASSPORT, optionalPassport.get());
            } else {
                enrolleeId = -1;
            }
        } else {
            enrolleeId = -1;
        }
        return enrolleeId;
    }

    /**
     * Put enrollee faculty and specialty in session boolean.
     *
     * @param enrolleeId the enrollee id
     * @param session    the session
     * @return the boolean
     * @throws ServiceException the service exception
     */
    default boolean putEnrolleeFacultyAndSpecialtyInSession(int enrolleeId, HttpSession session) throws ServiceException {
        FacultyService facultyService = FacultyServiceImpl.getInstance();
        SpecialtyService specialtyService = SpecialtyServiceImpl.getInstance();
        boolean isAdded;
        Optional<Faculty> optionalFaculty = facultyService.findEnrolleeFaculty(enrolleeId);
        Optional<Specialty> optionalSpecialty = specialtyService.findEnrolleeSpecialty(enrolleeId);
        if (optionalFaculty.isPresent() && optionalSpecialty.isPresent()) {
            session.setAttribute(AttributeName.FACULTY, optionalFaculty.get());
            session.setAttribute(AttributeName.SPECIALTY, optionalSpecialty.get());
            isAdded = true;
        } else {
            isAdded = false;
        }
        return isAdded;
    }

    /**
     * Put faculties specialties subjects in request boolean.
     *
     * @param request the request
     * @return the boolean
     * @throws ServiceException the service exception
     */
    default boolean putFacultiesSpecialtiesSubjectsInRequest(HttpServletRequest request) throws ServiceException {
        FacultyService facultyService = FacultyServiceImpl.getInstance();
        SpecialtyService specialtyService = SpecialtyServiceImpl.getInstance();
        SubjectService subjectService = SubjectServiceImpl.getInstance();
        boolean isAdded;
        List<Faculty> faculties = facultyService.findAllActiveFaculties();
        List<Specialty> specialties = specialtyService.findAllOpenSpecialties();
        List<Subject> subjects = subjectService.findAllSubjects();
        if (!faculties.isEmpty() && !specialties.isEmpty() && !subjects.isEmpty()) {
            request.setAttribute(AttributeName.FACULTIES, faculties);
            request.setAttribute(AttributeName.SPECIALTIES, specialties);
            request.setAttribute(AttributeName.SUBJECTS, subjects);
            isAdded = true;
        } else {
            isAdded = false;
        }
        return isAdded;
    }
}
