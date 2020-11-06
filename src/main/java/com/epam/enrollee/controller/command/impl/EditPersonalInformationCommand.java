package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.exception.CommandException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Passport;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.util.MapKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EditPersonalInformationCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        EnrolleeServiceImpl enrolleeService = new EnrolleeServiceImpl();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session = request.getSession();
        String page = null;
        Enrollee enrollee = (Enrollee) session.getAttribute(RequestParameters.ENROLLEE);
        try {
            parameters.put(MapKeys.FIRST_NAME, request.getParameter(RequestParameters.FIRST_NAME));
            parameters.put(MapKeys.LAST_NAME, request.getParameter(RequestParameters.LAST_NAME));
            parameters.put(MapKeys.MIDDLE_NAME, request.getParameter(RequestParameters.MIDDLE_NAME));
            parameters.put(MapKeys.PASSPORT_SERIES_AND_NUMBER, request.getParameter
                    (RequestParameters.PASSPORT_SERIES_AND_NUMBER));
            parameters.put(MapKeys.PERSONAL_NUMBER, request.getParameter(RequestParameters.PERSONAL_NUMBER));
            Map<String, String> checkedParameters = enrolleeService.checkEnrolleeParameters(parameters);
            if (checkedParameters.containsValue("")) {
                request.setAttribute(RequestParameters.PARAMETERS, checkedParameters);
                page = PagePath.EDIT_PROFILE;
            } else {
                Passport passport = (Passport) session.getAttribute(RequestParameters.PASSPORT);
                Optional<Enrollee> optionalEnrollee = enrolleeService.updateEnrolleeNameInformation
                        (enrollee, parameters);
                Optional<Passport> optionalPassport = enrolleeService.updateEnrolleePassportInformation
                        (passport, parameters);
                if (optionalEnrollee.isPresent() && optionalPassport.isPresent()) {
                    session.setAttribute(RequestParameters.ENROLLEE, optionalEnrollee.get());
                    session.setAttribute(RequestParameters.PASSPORT, optionalPassport.get());
                    page = PagePath.PROFILE;
                }
            }
        } catch (ServiceException e) {
            page = PagePath.ERROR_500;
        }
        return page;
    }
}