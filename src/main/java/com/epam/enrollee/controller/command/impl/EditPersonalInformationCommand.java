package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
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
    private static final String EMPTY_STRING = "";
    private static final String EDIT_PERSONAL_INFORMATION = "edit_personal_information";

    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeServiceImpl enrolleeService = new EnrolleeServiceImpl();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session = request.getSession();
        Router router;
        Passport passport;
        Enrollee enrollee;
            parameters.put(MapKeys.FIRST_NAME, request.getParameter(RequestParameters.FIRST_NAME));
            parameters.put(MapKeys.LAST_NAME, request.getParameter(RequestParameters.LAST_NAME));
            parameters.put(MapKeys.MIDDLE_NAME, request.getParameter(RequestParameters.MIDDLE_NAME));
            parameters.put(MapKeys.PASSPORT_SERIES_AND_NUMBER, request.getParameter(RequestParameters.PASSPORT_SERIES_AND_NUMBER));
            parameters.put(MapKeys.PERSONAL_NUMBER, request.getParameter(RequestParameters.PERSONAL_NUMBER));
           try {
            Map<String, String> checkedParameters = enrolleeService.checkParameters(parameters);
            if (checkedParameters.containsValue(EMPTY_STRING)) {
                request.setAttribute(RequestParameters.PARAMETERS, checkedParameters);
                request.setAttribute(RequestParameters.EDIT_PART, EDIT_PERSONAL_INFORMATION);
                router = new Router(PagePath.EDIT_PROFILE);
            } else {
                enrollee = (Enrollee) session.getAttribute(RequestParameters.ENROLLEE);
                passport = (Passport) session.getAttribute(RequestParameters.PASSPORT);
                Optional<Enrollee> optionalEnrollee = enrolleeService.updateEnrolleeNameInformation(enrollee, parameters);
                Optional<Passport> optionalPassport = enrolleeService.updateEnrolleePassportInformation(passport, parameters);
                if (optionalEnrollee.isPresent() && optionalPassport.isPresent()) {
                    session.removeAttribute(RequestParameters.ENROLLEE);
                    session.removeAttribute(RequestParameters.PASSPORT);
                    session.setAttribute(RequestParameters.ENROLLEE, optionalEnrollee.get());
                    session.setAttribute(RequestParameters.PASSPORT, optionalPassport.get());
                    router = new Router(PagePath.PROFILE);
                } else {
                    router = new Router(PagePath.ERROR_500);
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
