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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EditFacultyCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        FacultyService facultyService = FacultyServiceImpl.getInstance();
        Map<String, String> parameters = new HashMap<>();
        Router router;
        String facultyId = request.getParameter(RequestParameter.FACULTY_ID);
        parameters.put(MapKeys.FACULTY_ID, facultyId);
        parameters.put(MapKeys.FACULTY_NAME, request.getParameter(RequestParameter.FACULTY_NAME));
        parameters.put(MapKeys.FACULTY_DESCRIPTION, request.getParameter(RequestParameter.FACULTY_DESCRIPTION));
        try {
            Map<String, String> checkedParameters = facultyService.checkParameters(parameters);
            if (!checkedParameters.get(MapKeys.FACULTY_ID).equals(EMPTY_VALUE)) {
                if (checkedParameters.containsValue(EMPTY_VALUE)) {
                    request.setAttribute(AttributeName.PARAMETERS, checkedParameters);
                    Optional<Faculty> faculty = facultyService.findFacultyById(facultyId);
                    if (faculty.isPresent()) {
                        request.setAttribute(AttributeName.FACULTY, faculty.get());
                        router = new Router(PagePath.EDIT_FACULTY);
                    } else {
                        router = new Router(PagePath.ERROR);
                        logger.log(Level.ERROR, "impossible find chosen faculty.");
                    }
                } else {
                    if (facultyService.update(checkedParameters)) {
                        List<Faculty> faculties = facultyService.findAllActiveFaculties();
                        request.setAttribute(AttributeName.FACULTIES, faculties);
                        router = new Router(PagePath.ADMIN_FACULTIES);
                    } else {
                        router = new Router(PagePath.ERROR);
                        logger.log(Level.ERROR, "Impossible update faculty");
                    }
                }
            } else {
                router = new Router(PagePath.ERROR);
                logger.log(Level.ERROR, "Incorrect faculty id");
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.INFO, "Application error: ", e);
        }
        return router;
    }
}
