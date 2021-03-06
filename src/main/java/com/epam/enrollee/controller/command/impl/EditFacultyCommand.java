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
 * The type Edit faculty command.
 * A faculty can be edited by a user with the admin role. Parameters for updating come from the
 * request. If the faculty is successfully saved in the database, the user is redirected to the
 * faculties page. If the data is not entered correctly, the user is returned to the edit profile
 * page.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class EditFacultyCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        FacultyService facultyService = FacultyServiceImpl.getInstance();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session = request.getSession();
        Router router;
        String facultyId = request.getParameter(RequestParameter.FACULTY_ID);
        parameters.put(MapKeys.FACULTY_ID, facultyId);
        parameters.put(MapKeys.FACULTY_NAME, request.getParameter(RequestParameter.FACULTY_NAME));
        parameters.put(MapKeys.FACULTY_DESCRIPTION, request.getParameter(RequestParameter.FACULTY_DESCRIPTION));
        try {
            Map<String, String> checkedParameters = facultyService.checkParameters(parameters);
            if (!checkedParameters.get(MapKeys.FACULTY_ID).equals(EMPTY_VALUE)) {
                if (checkedParameters.containsValue(EMPTY_VALUE)) {
                    session.setAttribute(AttributeName.INCORRECT, true);
                    router = new Router(Router.Type.REDIRECT, PagePath.EDIT_FACULTY);
                } else {
                    if (session.getAttribute(AttributeName.INCORRECT) != null) {
                        session.removeAttribute(AttributeName.INCORRECT);
                    }
                    session.removeAttribute(AttributeName.FACULTY);
                    if (facultyService.update(checkedParameters)) {
                        session.removeAttribute(AttributeName.FACULTIES);
                        List<Faculty> faculties = facultyService.findAllActiveFaculties();
                        session.setAttribute(AttributeName.FACULTIES, faculties);
                        router = new Router(Router.Type.REDIRECT, PagePath.ADMIN_FACULTIES);
                    } else {
                        router = new Router(Router.Type.REDIRECT, PagePath.ERROR);
                        logger.log(Level.ERROR, "Impossible update faculty");
                    }
                }
            } else {
                router = new Router(Router.Type.REDIRECT, PagePath.ERROR);
                logger.log(Level.ERROR, "Incorrect faculty id");
            }
        } catch (ServiceException e) {
            router = new Router(Router.Type.REDIRECT, PagePath.ERROR_500);
            logger.log(Level.INFO, "Application error: ", e);
        }
        return router;
    }
}