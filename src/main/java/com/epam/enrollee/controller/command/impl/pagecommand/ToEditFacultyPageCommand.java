package com.epam.enrollee.controller.command.impl.pagecommand;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ToEditFacultyPageCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        FacultyServiceImpl facultyService = new FacultyServiceImpl();
        Router router;
        String facultyId = request.getParameter(RequestParameters.FACULTY_ID);
        try {
            if (facultyId != null) {
                Optional<Faculty> faculty = facultyService.findFacultyById(facultyId);
                if (faculty.isPresent()) {
                    request.setAttribute(RequestParameters.FACULTY, faculty.get());
                    router = new Router(PagePath.EDIT_FACULTY);
                } else {
                    router = new Router(PagePath.ERROR_500);
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
