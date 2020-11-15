package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Passport;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EditPersonalInformationCommand implements Command {

    private static Logger logger = LogManager.getLogger();
    private static final String EDIT_PERSONAL_INFORMATION = "edit_personal_information";

    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeServiceImpl enrolleeService = new EnrolleeServiceImpl();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session = request.getSession();
        Router router;
        Passport passport;
        Enrollee enrollee;
        parameters.put(MapKeys.FIRST_NAME, request.getParameter(RequestParameter.FIRST_NAME));
        parameters.put(MapKeys.LAST_NAME, request.getParameter(RequestParameter.LAST_NAME));
        parameters.put(MapKeys.MIDDLE_NAME, request.getParameter(RequestParameter.MIDDLE_NAME));
        parameters.put(MapKeys.PASSPORT_SERIES_AND_NUMBER, request.getParameter(RequestParameter.PASSPORT_SERIES_AND_NUMBER));
        parameters.put(MapKeys.PERSONAL_NUMBER, request.getParameter(RequestParameter.PERSONAL_NUMBER));
        try {
            Map<String, String> checkedParameters = enrolleeService.checkParameters(parameters);
            if (checkedParameters.containsValue(EMPTY_VALUE)) {
                request.setAttribute(AttributeName.PARAMETERS, checkedParameters);
                request.setAttribute(AttributeName.EDIT_PART, EDIT_PERSONAL_INFORMATION);
                router = new Router(PagePath.EDIT_PROFILE);
            } else {
                enrollee = (Enrollee) session.getAttribute(AttributeName.ENROLLEE);
                passport = (Passport) session.getAttribute(AttributeName.PASSPORT);
                Optional<Enrollee> optionalEnrollee = enrolleeService.updateEnrolleeNameInformation(enrollee, parameters);
                Optional<Passport> optionalPassport = enrolleeService.updateEnrolleePassportInformation(passport, parameters);
                if (optionalEnrollee.isPresent() && optionalPassport.isPresent()) {
                    session.removeAttribute(AttributeName.ENROLLEE);
                    session.removeAttribute(AttributeName.PASSPORT);
                    session.setAttribute(AttributeName.ENROLLEE, optionalEnrollee.get());
                    session.setAttribute(AttributeName.PASSPORT, optionalPassport.get());
                    router = new Router(PagePath.PROFILE);
                } else {
                    router = new Router(PagePath.ERROR);
                    logger.log(Level.ERROR, "Impossible add updated enrollee personal information in db");
                }
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.INFO, "Application error: ", e);
        }
        return router;
    }
}
