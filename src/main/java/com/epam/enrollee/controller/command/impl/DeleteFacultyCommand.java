package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeleteFacultyCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        FacultyServiceImpl facultyService = new FacultyServiceImpl();
        Router router = null;
        String stringFacultyId = request.getParameter(RequestParameters.FACULTY_ID);
        int facultyId = Integer.parseInt(stringFacultyId);
        try {
            if(facultyService.checkFacultyAplications(facultyId)){
                request.setAttribute(RequestParameters.HAS_APPLICATION, true);
                List<Faculty> faculties = facultyService.findAll();
                request.setAttribute(RequestParameters.FACULTIES, faculties);
                router = new Router(PagePath.ADMIN_FACULTIES);
            } else {
                if (facultyService.remove(facultyId)) {
                    List<Faculty> faculties = facultyService.findAll();
                    request.setAttribute(RequestParameters.FACULTIES, faculties);
                    router = new Router(PagePath.ADMIN_FACULTIES);
                }
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "application error: ", e);
        }
        return router;
    }
}
