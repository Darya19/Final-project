package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.*;
import com.epam.enrollee.model.service.impl.EnrolleeMarkRegisterServiceImpl;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import com.epam.enrollee.model.type.RoleType;
import com.epam.enrollee.util.MapKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.enrollee.controller.command.PagePath.*;


public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        EnrolleeServiceImpl enrolleeService = new EnrolleeServiceImpl();
        EnrolleeMarkRegisterServiceImpl markRegisterService = new EnrolleeMarkRegisterServiceImpl();
        FacultyServiceImpl facultyService = new FacultyServiceImpl();
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        HttpSession session;
        Map<String, String> parameters = new HashMap<>();
        Enrollee enrollee = null;
        String page = null;
        int enrolleeId = 0;
        String email = request.getParameter(RequestParameters.EMAIL);
        String password = request.getParameter(RequestParameters.PASSWORD);
        try {
            if (enrolleeService.checkEmailAndPassword(email, password)) {
                Optional<User> optionalUser = enrolleeService.findUserByEmail(email);
                if (optionalUser.isPresent()) {
                    session = request.getSession();
                    if (optionalUser.get().getRole().equals(RoleType.USER)) {
                        Optional<Enrollee> optionalEnrollee = enrolleeService.createEnrolleeByEmail(email);
                        if (optionalEnrollee.isPresent()) {
                            enrollee = optionalEnrollee.get();
                            enrolleeId = enrollee.getUserId();
                            session.setAttribute(RequestParameters.ENROLLEE, enrollee);}
                            Optional<EnrolleeMarkRegister> optionalEnrolleeRegister = markRegisterService
                                    .findEnrolleeMarkRegister(enrolleeId);
                            Optional<Passport> optionalPassport = enrolleeService.findEnrolleePassport
                                    (enrolleeId);
                            Optional<Faculty> optionalFaculty = facultyService.findFacultyById
                                    (enrollee.getChosenFacultyId());
                            Optional<Specialty> optionalSpecialty = specialtyService.findEnrolleeSpecialty
                                    (enrollee.getChosenSpecialtyId());
                            if (optionalEnrolleeRegister.isPresent() && optionalPassport.isPresent()
                                    && optionalFaculty.isPresent() && optionalSpecialty.isPresent()) {
                                session.setAttribute(REGISTER,
                                        optionalEnrolleeRegister.get().getTestsSubjectsAndMarks());
                                session.setAttribute(RequestParameters.PASSPORT, optionalPassport.get());
                                session.setAttribute(RequestParameters.FACULTY, optionalFaculty.get());
                                session.setAttribute(RequestParameters.SPECIALTY, optionalSpecialty.get());
                            }
                            page = PROFILE;
                    }
                    if (optionalUser.get().getRole().equals(RoleType.ADMIN)) {
                        User user = optionalUser.get();
                        session.setAttribute(RequestParameters.ENROLLEE, user);
                        List<Faculty> faculties = facultyService.findAll();
                        request.setAttribute(FACULTIES, faculties);
                        page = ADMIN_FACULTIES;
                    }
                }
            } else {
                parameters.put(MapKeys.EMAIL, email);
                parameters.put(MapKeys.PASSWORD, password);
                request.setAttribute(RequestParameters.PARAMETERS, parameters);
                page = PagePath.LOGIN;
            }
        } catch (ServiceException e) {
            page = ERROR_500;
        }
        return page;
    }
}
