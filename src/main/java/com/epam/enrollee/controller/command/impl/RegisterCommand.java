package com.epam.enrollee.controller.command.impl;


import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
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

public class RegisterCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeServiceImpl enrolleeService = new EnrolleeServiceImpl();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session;
        Router router;
        int enrolleeId;
        boolean isRegister;
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
            parameters = enrolleeService.checkParameters(parameters);
            if (parameters.containsValue("")) {
                request.setAttribute(RequestParameters.PARAMETERS, parameters);
                if (putFacultiesSpecialtiesSubjectsInRequest(request)) {
                    router = new Router(PagePath.REGISTER);
                } else {
                    router = new Router(PagePath.ERROR_500);
                    logger.log(Level.ERROR, "Impossible create enrollee or register");
                }
            } else {
                session = request.getSession();
                isRegister = enrolleeService.registerEnrollee(parameters);
                if (isRegister) {
                    enrolleeId = putInSessionEnrolleeAndPassportAndMarks
                            (parameters.get(MapKeys.EMAIL), session);
                    if (enrolleeId > 0) {
                        if (putEnrolleeFacultyAndSpecialtyInSession(enrolleeId, session)) {
                            session.removeAttribute(RequestParameters.ROLE);
                            session.setAttribute(RequestParameters.ROLE, RoleType.USER);
                            router = new Router(PagePath.PROFILE);
                        } else {
                            router = new Router(PagePath.ERROR_500);
                            logger.log(Level.ERROR, "Impossible create faculty or specialty for enrollee");
                        }
                    } else router = new Router(PagePath.ERROR_500);
                    logger.log(Level.ERROR, "Incorrect enrollee id");
                } else {
                    router = new Router(PagePath.ERROR_500);
                    logger.log(Level.ERROR, "Impossible add enroollee in db");
                }
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}
