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
import java.util.Optional;

public class EditFacultyCommand implements Command {

    private static Logger logger = LogManager.getLogger();
    private static final String EMPTY_STRING = "";

    @Override
    public Router execute(HttpServletRequest request) {
        FacultyServiceImpl facultyService = new FacultyServiceImpl();
        Map<String, String> parameters = new HashMap<>();
        Router router;
        int facultyId = Integer.parseInt(request.getParameter(RequestParameters.FACULTY_ID));
        try {
            parameters.put(MapKeys.FACULTY_NAME, request.getParameter(RequestParameters.FACULTY_NAME));
            parameters.put(MapKeys.FACULTY_DESCRIPTION, request.getParameter
                    (RequestParameters.FACULTY_DESCRIPTION));

            Map<String, String> checkedParameters = facultyService.checkParameters(parameters);
            if (checkedParameters.containsValue(EMPTY_STRING)) {
                request.setAttribute(RequestParameters.PARAMETERS, checkedParameters);
                Optional<Faculty> faculty = facultyService.findFacultyById(facultyId);
                if (faculty.isPresent()) {
                    request.setAttribute(RequestParameters.FACULTY, faculty.get());
                    router = new Router(PagePath.EDIT_FACULTY);
                } else {
                    router = new Router(PagePath.ERROR_500);
                    logger.log(Level.ERROR, "impossible find chosen faculty.");
                }
            } else {
                if (facultyService.update(facultyId, checkedParameters)) {
                    List<Faculty> faculties = facultyService.findAll();
                    request.setAttribute(RequestParameters.FACULTIES, faculties);
                    router = new Router(PagePath.ADMIN_FACULTIES);
                } else {
                    router = new Router(PagePath.ERROR_500);
                    logger.log(Level.ERROR, "impossible update faculty");
                }
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.INFO, "Application error: ", e);
        }
        return router;
    }
}
