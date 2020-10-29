package com.epam.enrollee.controller.command.impl;


import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.exception.CommandException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Address;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Passport;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RegisterCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        EnrolleeServiceImpl enrolleeService = new EnrolleeServiceImpl();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session;
        String page = null;
        parameters.put(RequestParameters.FIRST_NAME, request.getParameter(RequestParameters.FIRST_NAME));
        parameters.put(RequestParameters.LAST_NAME, request.getParameter(RequestParameters.LAST_NAME));
        parameters.put(RequestParameters.MIDDLE_NAME, request.getParameter(RequestParameters.MIDDLE_NAME));
        parameters.put(RequestParameters.PASSPORT_SERIES_AND_NUMBER, request.getParameter(RequestParameters.PASSPORT_SERIES_AND_NUMBER));
        parameters.put(RequestParameters.PERSONAL_NUMBER, request.getParameter(RequestParameters.PERSONAL_NUMBER));
        parameters.put(RequestParameters.COUNTRY, request.getParameter(RequestParameters.COUNTRY));
        parameters.put(RequestParameters.CITY, request.getParameter(RequestParameters.CITY));
        parameters.put(RequestParameters.STREET, request.getParameter(RequestParameters.STREET));
        parameters.put(RequestParameters.HOUSE, request.getParameter(RequestParameters.HOUSE));
        parameters.put(RequestParameters.FLAT, request.getParameter(RequestParameters.FLAT));
        parameters.put(RequestParameters.PHONE_NUMBER, request.getParameter(RequestParameters.PHONE_NUMBER));
        parameters.put(RequestParameters.FACULTY_ID, request.getParameter(RequestParameters.FACULTY_ID));
        parameters.put(RequestParameters.SPECIALTY_ID, request.getParameter(RequestParameters.SPECIALTY_ID));
        parameters.put(RequestParameters.SUBJECT_ID_1, request.getParameter(RequestParameters.SUBJECT_ID_1));
        parameters.put(RequestParameters.MARK_1, request.getParameter(RequestParameters.MARK_1));
        parameters.put(RequestParameters.SUBJECT_ID_2, request.getParameter(RequestParameters.SUBJECT_ID_2));
        parameters.put(RequestParameters.MARK_2, request.getParameter(RequestParameters.MARK_2));
        parameters.put(RequestParameters.SUBJECT_ID_3, request.getParameter(RequestParameters.SUBJECT_ID_3));
        parameters.put(RequestParameters.MARK_3, request.getParameter(RequestParameters.MARK_3));
        parameters.put(RequestParameters.SUBJECT_ID_4, request.getParameter(RequestParameters.SUBJECT_ID_4));
        parameters.put(RequestParameters.MARK_4, request.getParameter(RequestParameters.MARK_4));
        parameters.put(RequestParameters.PASSWORD, request.getParameter(RequestParameters.PASSWORD));
        parameters.put(RequestParameters.REPEATED_PASSWORD, request.getParameter(RequestParameters.REPEATED_PASSWORD));
        parameters.put(RequestParameters.EMAIL, request.getParameter(RequestParameters.EMAIL));
        try {
            parameters = enrolleeService.checkEnrolleeParameters(parameters);
            if (parameters.containsValue("")) {
                request.setAttribute(RequestParameters.PARAMETERS, parameters);
                page = PagePath.REGISTER;
            } else {
                Optional<Map<String, Object>> optionalObjectMap = enrolleeService.create(parameters);
                if (optionalObjectMap.isPresent()) {
                    Map<String, Object> objectMap = optionalObjectMap.get();
                    session = request.getSession();
                    session.setAttribute(RequestParameters.ENROLLEE, objectMap.get(RequestParameters.ENROLLEE));
                    session.setAttribute(RequestParameters.PASSPORT, objectMap.get(RequestParameters.PASSPORT));
                    session.setAttribute(RequestParameters.ADDRESS,objectMap.get(RequestParameters.ADDRESS));
                    page = PagePath.PROFILE;
                } else {
                    page = PagePath.ERROR_500;
                }
            }
        } catch (ServiceException e) {
            page = PagePath.ERROR_500;
        }
        return page;
    }
}
