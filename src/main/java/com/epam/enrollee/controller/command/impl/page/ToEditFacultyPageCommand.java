package com.epam.enrollee.controller.command.impl.page;

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
import java.util.Optional;

/**
 * The type To edit faculty page command.
 * Command to go to the edit faculties page. Command used by user with role admin.
 *
 *  @author Darya Shcherbina
 *  @version 1.0
 */
public class ToEditFacultyPageCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        FacultyService facultyService = FacultyServiceImpl.getInstance();
        Router router;
        String facultyId = request.getParameter(RequestParameter.FACULTY_ID);
        try {
            if (facultyId != null) {
                Optional<Faculty> faculty = facultyService.findFacultyById(facultyId);
                if (faculty.isPresent()) {
                    request.setAttribute(AttributeName.FACULTY, faculty.get());
                    router = new Router(PagePath.EDIT_FACULTY);
                } else {
                    router = new Router(PagePath.ERROR);
                    logger.log(Level.ERROR, "Impossible find chosen faculty in db.");
                }
            } else {
                router = new Router(PagePath.EDIT_FACULTY);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}