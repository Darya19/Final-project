package com.epam.enrollee.controller.command.impl;


import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.service.EnrolleeService;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.model.type.RoleType;
import com.epam.enrollee.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Register command.
 * The command processes the data received with the request. In case of successful validation,
 * it saves them to the database and creates an object for the possible further work of the user
 * in the system, and also redirects the user to the profile page. If incorrect data is entered,
 * the user is returned to the registration page.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class RegisterCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeService enrolleeService = EnrolleeServiceImpl.getInstance();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session = request.getSession();
        Router router;
        int enrolleeId;
        boolean isRegister;
        parameters.put(MapKeys.FIRST_NAME, request.getParameter(RequestParameter.FIRST_NAME));
        parameters.put(MapKeys.LAST_NAME, request.getParameter(RequestParameter.LAST_NAME));
        parameters.put(MapKeys.MIDDLE_NAME, request.getParameter(RequestParameter.MIDDLE_NAME));
        parameters.put(MapKeys.PASSPORT_SERIES_AND_NUMBER, request.getParameter
                (RequestParameter.PASSPORT_SERIES_AND_NUMBER));
        parameters.put(MapKeys.PERSONAL_NUMBER, request.getParameter(RequestParameter.PERSONAL_NUMBER));
        parameters.put(MapKeys.FACULTY_ID, request.getParameter(RequestParameter.FACULTY_ID));
        parameters.put(MapKeys.SPECIALTY_ID, request.getParameter(RequestParameter.SPECIALTY_ID));
        parameters.put(MapKeys.SUBJECT_ID_1, request.getParameter(RequestParameter.SUBJECT_ID_1));
        parameters.put(MapKeys.MARK_1, request.getParameter(RequestParameter.MARK_1));
        parameters.put(MapKeys.SUBJECT_ID_2, request.getParameter(RequestParameter.SUBJECT_ID_2));
        parameters.put(MapKeys.MARK_2, request.getParameter(RequestParameter.MARK_2));
        parameters.put(MapKeys.SUBJECT_ID_3, request.getParameter(RequestParameter.SUBJECT_ID_3));
        parameters.put(MapKeys.MARK_3, request.getParameter(RequestParameter.MARK_3));
        parameters.put(MapKeys.SUBJECT_ID_4, request.getParameter(RequestParameter.SUBJECT_ID_4));
        parameters.put(MapKeys.MARK_4, request.getParameter(RequestParameter.MARK_4));
        parameters.put(MapKeys.PASSWORD, request.getParameter(RequestParameter.PASSWORD));
        parameters.put(MapKeys.REPEATED_PASSWORD, request.getParameter(RequestParameter.REPEATED_PASSWORD));
        parameters.put(MapKeys.EMAIL, request.getParameter(RequestParameter.EMAIL));
        try {
            if (session.getAttribute(AttributeName.PARAMETERS) != null) {
                session.removeAttribute(AttributeName.PARAMETERS);
            }
            parameters = enrolleeService.checkParameters(parameters);
            if (parameters.containsValue("")) {
                session.setAttribute(AttributeName.PARAMETERS, parameters);
                router = new Router(Router.Type.REDIRECT, PagePath.REGISTER);
            } else {
                removeFacultiesSpecialtiesSubjectsFromSession(session);
                isRegister = enrolleeService.create(parameters);
                if (isRegister) {
                    enrolleeId = putInSessionEnrolleeAndPassportAndMarks
                            (parameters.get(MapKeys.EMAIL), session);
                    if (enrolleeId > 0) {
                        if (putEnrolleeFacultyAndSpecialtyInSession(enrolleeId, session)) {
                            session.removeAttribute(AttributeName.ROLE);
                            session.setAttribute(AttributeName.ROLE, RoleType.USER);
                            router = new Router(Router.Type.REDIRECT, PagePath.PROFILE);
                        } else {
                            router = new Router(Router.Type.REDIRECT, PagePath.ERROR);
                            logger.log(Level.ERROR, "Impossible create faculty or specialty for enrollee");
                        }
                    } else {
                        router = new Router(Router.Type.REDIRECT, PagePath.ERROR);
                        logger.log(Level.ERROR, "Incorrect enrollee id");
                    }
                } else {
                    router = new Router(Router.Type.REDIRECT, PagePath.ERROR);
                    logger.log(Level.ERROR, "Impossible add enroollee in db");
                }
            }
        } catch (ServiceException e) {
            router = new Router(Router.Type.REDIRECT, PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}