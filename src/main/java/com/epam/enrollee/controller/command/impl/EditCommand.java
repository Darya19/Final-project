package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.exception.CommandException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.*;
import com.epam.enrollee.model.service.impl.EnrolleeMarkRegisterServiceImpl;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import com.epam.enrollee.util.MapKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EditCommand implements Command {

    private static final String ENROLLEE = "enrollee";
    private static final String PASSPORT = "passport";
    private static final String SPECIALTIES = "specialties";
    private static final String REGISTER = "register";
    private static final String PARAMETERS = "parameters";
    private static final String EDIT = "edit_part";
    private static final String EDIT_PERSONAL_INFORMATION = "edit_personal_information";
    private static final String EDIT_MARKS = "edit_marks";


    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        EnrolleeServiceImpl enrolleeService = new EnrolleeServiceImpl();
        EnrolleeMarkRegisterServiceImpl registerService = new EnrolleeMarkRegisterServiceImpl();
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session = request.getSession();
        String page = null;
        String firstName = null;
        String specialtyId = null;

        if (request.getParameter(RequestParameters.FIRST_NAME) != null) {
            firstName = request.getParameter(RequestParameters.FIRST_NAME);
        }
        if (request.getParameter(RequestParameters.SPECIALTY_ID) != null) {
            specialtyId = request.getParameter(RequestParameters.SPECIALTY_ID);
        }
        Enrollee enrollee = (Enrollee) session.getAttribute(ENROLLEE);
        try {
            if (firstName != null) {
                parameters.put(MapKeys.FIRST_NAME, request.getParameter(RequestParameters.FIRST_NAME));
                parameters.put(MapKeys.LAST_NAME, request.getParameter(RequestParameters.LAST_NAME));
                parameters.put(MapKeys.MIDDLE_NAME, request.getParameter(RequestParameters.MIDDLE_NAME));
                parameters.put(MapKeys.PASSPORT_SERIES_AND_NUMBER, request.getParameter
                        (RequestParameters.PASSPORT_SERIES_AND_NUMBER));
                parameters.put(MapKeys.PERSONAL_NUMBER, request.getParameter(RequestParameters.PERSONAL_NUMBER));
                Map<String, String> checkedParameters = enrolleeService.checkEnrolleeParameters(parameters);
                if (checkedParameters.containsValue("")) {
                    request.setAttribute(PARAMETERS, checkedParameters);
                    request.setAttribute(EDIT, EDIT_PERSONAL_INFORMATION);
                    page = PagePath.EDIT_PROFILE;
                } else {
                    Passport passport = (Passport) session.getAttribute(PASSPORT);
                    Optional<Enrollee> optionalEnrollee = enrolleeService.updateEnrolleeNameInformation
                            (enrollee, parameters);
                    Optional<Passport> optionalPassport = enrolleeService.updateEnrolleePassportInformation
                            (passport, parameters);
                    if (optionalEnrollee.isPresent() && optionalPassport.isPresent()) {
                        session.setAttribute(ENROLLEE, optionalEnrollee.get());
                        session.setAttribute(PASSPORT, optionalPassport.get());
                        page = PagePath.PROFILE;
                    }
                }
            } else {
                if (specialtyId != null) {
                    Optional<Enrollee> newEnrollee = enrolleeService.updateEnrolleeSpecialty
                            (enrollee, specialtyId);
                    if (newEnrollee.isPresent()) {
                        session.setAttribute(ENROLLEE, newEnrollee.get());
                        int facultyId = enrollee.getChosenFacultyId();
                        List<Specialty> specialties = specialtyService.findSpecialtiesOfFaculty(facultyId);
                        request.setAttribute(SPECIALTIES, specialties);
                        page = PagePath.PROFILE;
                    }
                } else {
                    Map<Subject, Integer> register = (Map<Subject, Integer>) session.getAttribute(REGISTER);
                    for (Subject subject : register.keySet()) {
                        String key = String.valueOf(subject.getSubjectId());
                        parameters.put(key, request.getParameter(key));
                    }
                    parameters = registerService.checkMarks(parameters);
                    if (parameters.containsValue("")) {
                        request.setAttribute(PARAMETERS, parameters);
                        request.setAttribute(EDIT, EDIT_MARKS);
                        page = PagePath.EDIT_PROFILE;
                    } else {
                        Optional<EnrolleeMarkRegister> markRegister = registerService.updateEnrolleRegister
                                (enrollee.getUserId(), parameters);
                        session.setAttribute(REGISTER, markRegister.get().getTestsSubjectsAndMarks());
                    }
                }
            }
            return page;
        } catch (ServiceException e) {
            page = PagePath.ERROR_500;
        }
        return page;
    }
}
