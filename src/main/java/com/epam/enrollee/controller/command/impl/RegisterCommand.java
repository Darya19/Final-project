package com.epam.enrollee.controller.command.impl;


import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.CommandException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.*;
import com.epam.enrollee.model.service.impl.EnrolleeMarkRegisterServiceImpl;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import com.epam.enrollee.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RegisterCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeServiceImpl enrolleeService = new EnrolleeServiceImpl();
        EnrolleeMarkRegisterServiceImpl markRegisterService = new EnrolleeMarkRegisterServiceImpl();
        FacultyServiceImpl facultyService = new FacultyServiceImpl();
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session;
        Router router;
        int enrolleeId = 0;
        boolean isRegister;
        Enrollee enrollee = null;
        EnrolleeMarkRegister register = null;
        parameters.put(MapKeys.FIRST_NAME, request.getParameter(RequestParameters.FIRST_NAME));
        parameters.put(MapKeys.LAST_NAME, request.getParameter(RequestParameters.LAST_NAME));
        parameters.put(MapKeys.MIDDLE_NAME, request.getParameter(RequestParameters.MIDDLE_NAME));
        parameters.put(MapKeys.PASSPORT_SERIES_AND_NUMBER, request.getParameter
                (RequestParameters.PASSPORT_SERIES_AND_NUMBER));
        parameters.put(MapKeys.PERSONAL_NUMBER, request.getParameter(RequestParameters.PERSONAL_NUMBER));
        parameters.put(MapKeys.FACULTY_ID, request.getParameter(RequestParameters.FACULTY_ID));
        parameters.put(MapKeys.SPECIALTY_ID, request.getParameter(RequestParameters.SPECIALTY_ID));
        parameters.put(MapKeys.SUBJECT_ID_1, request.getParameter(RequestParameters.SUBJECT_ID_1));
        parameters.put(MapKeys.MARK_1, request.getParameter(RequestParameters.MARK_1));
        parameters.put(MapKeys.SUBJECT_ID_2, request.getParameter(RequestParameters.SUBJECT_ID_2));
        parameters.put(MapKeys.MARK_2, request.getParameter(RequestParameters.MARK_2));
        parameters.put(MapKeys.SUBJECT_ID_3, request.getParameter(RequestParameters.SUBJECT_ID_3));
        parameters.put(MapKeys.MARK_3, request.getParameter(RequestParameters.MARK_3));
        parameters.put(MapKeys.SUBJECT_ID_4, request.getParameter(RequestParameters.SUBJECT_ID_4));
        parameters.put(MapKeys.MARK_4, request.getParameter(RequestParameters.MARK_4));
        parameters.put(MapKeys.PASSWORD, request.getParameter(RequestParameters.PASSWORD));
        parameters.put(MapKeys.REPEATED_PASSWORD, request.getParameter(RequestParameters.REPEATED_PASSWORD));
        parameters.put(MapKeys.EMAIL, request.getParameter(RequestParameters.EMAIL));
        try {
            parameters = enrolleeService.checkEnrolleeParameters(parameters);
            if (parameters.containsValue("")) {
                request.setAttribute(RequestParameters.PARAMETERS, parameters);
                router = new Router(Router.Type.REDIRECT, PagePath.REGISTER);
            } else {
                session = request.getSession();
                isRegister = enrolleeService.registerEnrollee(parameters);
                if (isRegister) {
                    Optional<Enrollee> optionalEnrollee = enrolleeService.createEnrolleeByEmail
                            (parameters.get(MapKeys.EMAIL));
                    if (optionalEnrollee.isPresent()) {
                        enrollee = optionalEnrollee.get();
                        enrolleeId = enrollee.getUserId();
                    }
                    Optional<EnrolleeMarkRegister> optionalEnrolleeRegister = markRegisterService
                            .findEnrolleeMarkRegister(enrolleeId);
                    if (optionalEnrolleeRegister.isPresent()) {
                        register = optionalEnrolleeRegister.get();
                        register = markRegisterService.calculateEnrolleeAverageMark(register);
                    }
                    Optional<Passport> optionalPassport = enrolleeService.findEnrolleePassport(enrolleeId);
                    Optional<Faculty> optionalFaculty = facultyService.findFacultyById
                            (enrollee.getChosenFacultyId());
                    Optional<Specialty> optionalSpecialty = specialtyService.findEnrolleeSpecialty
                            (enrollee.getChosenSpecialtyId());
                    if (optionalPassport.isPresent() && optionalFaculty.isPresent()
                            && optionalSpecialty.isPresent()) {
                        session.setAttribute(RequestParameters.ENROLLEE, enrollee);
                        session.setAttribute(RequestParameters.REGISTER, register);
                        session.setAttribute(RequestParameters.PASSPORT, optionalPassport.get());
                        request.setAttribute(RequestParameters.FACULTY, optionalFaculty.get());
                        request.setAttribute(RequestParameters.SPECIALTY, optionalSpecialty.get());
                    }
                    router = new Router(Router.Type.REDIRECT, PagePath.PROFILE);
                } else {
                   router = new Router(Router.Type.REDIRECT, PagePath.ERROR_500);
                }
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}
