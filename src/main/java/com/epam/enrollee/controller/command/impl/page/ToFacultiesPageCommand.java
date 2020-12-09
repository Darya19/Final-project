package com.epam.enrollee.controller.command.impl.page;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
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
 * The type To faculties page command.
 * Command to go to the faculties page. Command used by user with role user, guest, admin.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class ToFacultiesPageCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        FacultyService facultyService = FacultyServiceImpl.getInstance();
        Router router;
        try {
            List<Faculty> faculties = facultyService.findAllActiveFaculties();
            request.setAttribute(AttributeName.FACULTIES, faculties);
            router = new Router(PagePath.FACULTIES);
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}