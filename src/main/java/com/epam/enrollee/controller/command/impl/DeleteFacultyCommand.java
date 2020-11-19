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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The type Delete faculty command.
 * Delete a faculty, the faculty id come with request from user.
 * The command can be used by user with role admin. If the faculty is deleted successfully,
 * the faculty don't displays at the page faculties. The faculty is not deleted from the database,
 * but is marked with a status delete
 *
 *  @author Darya Shcherbina
 *  @version 1.0
 */
public class DeleteFacultyCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        FacultyService facultyService = FacultyServiceImpl.getInstance();
        Router router;
        String facultyId = request.getParameter(RequestParameter.FACULTY_ID);
        try {
            if (facultyService.checkConsideredApplications(facultyId)) {
                request.setAttribute(AttributeName.HAS_APPLICATION, true);
                List<Faculty> faculties = facultyService.findAllActiveFaculties();
                request.setAttribute(AttributeName.FACULTIES, faculties);
                router = new Router(PagePath.ADMIN_FACULTIES);
            } else {
                if (facultyService.removeFacultyAndItsSpecialties(facultyId)) {
                    List<Faculty> faculties = facultyService.findAllActiveFaculties();
                    request.setAttribute(AttributeName.FACULTIES, faculties);
                    router = new Router(PagePath.ADMIN_FACULTIES);
                } else {
                    router = new Router(PagePath.ERROR);
                    logger.log(Level.ERROR, "Impossible delete specialty");
                }
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error: ", e);
        }
        return router;
    }
}