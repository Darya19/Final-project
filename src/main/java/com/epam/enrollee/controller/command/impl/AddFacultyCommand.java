package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import com.epam.enrollee.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFacultyCommand implements Command {

    private static final String EMPTY_STRING = "";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        FacultyServiceImpl facultyService = new FacultyServiceImpl();
        Map<String, String> parameters = new HashMap<>();
        Router router;
        parameters.put(MapKeys.FACULTY_NAME, request.getParameter(RequestParameters.FACULTY_NAME));
        parameters.put(MapKeys.FACULTY_DESCRIPTION, request.getParameter(RequestParameters.FACULTY_DESCRIPTION));
        parameters = facultyService.checkParameters(parameters);
        if (parameters.containsValue(EMPTY_STRING)) {
            request.setAttribute(RequestParameters.PARAMETERS, parameters);
            router = new Router(PagePath.EDIT_FACULTY);
        } else {
            try {
                if (facultyService.create(parameters)) {
                    List<Faculty> faculties = facultyService.findAll();
                    request.setAttribute(RequestParameters.FACULTIES, faculties);
                    router = new Router(PagePath.FACULTIES);
                } else {
                    router = new Router(PagePath.ERROR_500);
                    logger.log(Level.ERROR, "Impossible add faculty to db");
                }
            } catch (ServiceException e) {
                router = new Router(PagePath.ERROR_500);
                logger.log(Level.ERROR, "Application error: ", e);
            }
        }
        return router;
    }
}
