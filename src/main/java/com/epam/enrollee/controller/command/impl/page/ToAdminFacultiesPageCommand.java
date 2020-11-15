package com.epam.enrollee.controller.command.impl.page;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ToAdminFacultiesPageCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        FacultyServiceImpl facultyService = FacultyServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Router router;
        try {
            if (session.getAttribute(AttributeName.FACULTY_ID) != null) {
                session.removeAttribute(AttributeName.FACULTY_ID);
            }
            List<Faculty> faculties = facultyService.findAllActiveFaculties();
            request.setAttribute(AttributeName.FACULTIES, faculties);
            router = new Router(PagePath.ADMIN_FACULTIES);
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}
