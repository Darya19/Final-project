package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.service.EnrolleeMarkRegisterService;
import com.epam.enrollee.model.service.EnrolleeService;
import com.epam.enrollee.model.service.impl.EnrolleeMarkRegisterServiceImpl;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.model.type.ApplicationStatus;
import com.epam.enrollee.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The type New application command.
 * The command creates a new application for the applicant if the old application is archived.
 * The command can be used by user with role user.If the application is successfully created
 * and saved in the database, it is displayed in the list of applications for the selected
 * specialty and in the user profile, the old application is deleted.
 */
public class NewApplicationCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeService enrolleeService = EnrolleeServiceImpl.getInstance();
        EnrolleeMarkRegisterService registerService = EnrolleeMarkRegisterServiceImpl.getInstance();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session = request.getSession();
        Router router;
        parameters.put(MapKeys.SPECIALTY_ID, request.getParameter(RequestParameter.SPECIALTY_ID));
        parameters.put(MapKeys.FACULTY_ID, request.getParameter(RequestParameter.FACULTY_ID));
        parameters.put(MapKeys.SUBJECT_ID_1, request.getParameter(RequestParameter.SUBJECT_ID_1));
        parameters.put(MapKeys.MARK_1, request.getParameter(RequestParameter.MARK_1));
        parameters.put(MapKeys.SUBJECT_ID_2, request.getParameter(RequestParameter.SUBJECT_ID_2));
        parameters.put(MapKeys.MARK_2, request.getParameter(RequestParameter.MARK_2));
        parameters.put(MapKeys.SUBJECT_ID_3, request.getParameter(RequestParameter.SUBJECT_ID_3));
        parameters.put(MapKeys.MARK_3, request.getParameter(RequestParameter.MARK_3));
        parameters.put(MapKeys.SUBJECT_ID_4, request.getParameter(RequestParameter.SUBJECT_ID_4));
        parameters.put(MapKeys.MARK_4, request.getParameter(RequestParameter.MARK_4));
        try {
            Enrollee enrollee = (Enrollee) session.getAttribute(AttributeName.ENROLLEE);
            parameters = enrolleeService.checkParameters(parameters);
            if (parameters.containsValue("")) {
                router = new Router(Router.Type.REDIRECT, PagePath.EDIT_APPLICATION);
            } else {
                removeFacultiesSpecialtiesSubjectsFromSession(session);
                Optional<EnrolleeMarkRegister> register = enrolleeService.updateEnrolleeApplication(enrollee, parameters);
                if (register.isPresent()) {
                    registerService.calculateEnrolleeAverageMark(register.get());
                    session.removeAttribute(AttributeName.REGISTER);
                    session.setAttribute(AttributeName.REGISTER, register.get());
                    session.removeAttribute(AttributeName.SPECIALTY);
                    session.removeAttribute(AttributeName.FACULTY);
                    putEnrolleeFacultyAndSpecialtyInSession(enrollee.getUserId(), session);
                    enrollee.setApplicationStatus(ApplicationStatus.CONSIDERED);
                    router = new Router(Router.Type.REDIRECT, PagePath.PROFILE);
                } else {
                    router = new Router(Router.Type.REDIRECT, PagePath.ERROR);
                    logger.log(Level.ERROR, "Incorrect enrollee parameters");
                }
            }
        } catch (ServiceException e) {
            router = new Router(Router.Type.REDIRECT, PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}