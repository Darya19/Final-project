package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.service.FacultyService;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import com.epam.enrollee.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Add faculty command.
 * A faculty can be added by a user with the admin role. If the new faculty is successfully
 * saved in the database, the user is redirected to the faculties page, otherwise returns to edit
 * faculty page.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class AddFacultyCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        FacultyService facultyService = FacultyServiceImpl.getInstance();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session = request.getSession();
        Router router;
        parameters.put(MapKeys.FACULTY_NAME, request.getParameter(RequestParameter.FACULTY_NAME));
        parameters.put(MapKeys.FACULTY_DESCRIPTION, request.getParameter(RequestParameter.FACULTY_DESCRIPTION));
        try {
            parameters = facultyService.checkParameters(parameters);
            if (parameters.containsValue(EMPTY_VALUE)) {
                session.setAttribute(AttributeName.PARAMETERS, parameters);
                router = new Router(Router.Type.REDIRECT, PagePath.EDIT_FACULTY);
            } else {
                if (facultyService.create(parameters)) {
                    if (session.getAttribute(AttributeName.PARAMETERS) != null) {
                        session.removeAttribute(AttributeName.PARAMETERS);
                    }
                    session.removeAttribute(AttributeName.FACULTIES);
                    List<Faculty> faculties = facultyService.findAllActiveFaculties();
                    session.setAttribute(AttributeName.FACULTIES, faculties);
                    router = new Router(Router.Type.REDIRECT, PagePath.ADMIN_FACULTIES);
                } else {
                    router = new Router(Router.Type.REDIRECT, PagePath.ERROR);
                    logger.log(Level.ERROR, "Impossible add faculty to db");
                }
            }
        } catch (ServiceException e) {
            router = new Router(Router.Type.REDIRECT, PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error: ", e);
        }
        return router;
    }
}