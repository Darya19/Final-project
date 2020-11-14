package com.epam.enrollee.controller.command.impl.page;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class ToRegisterPageCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        try {
            if (putFacultiesSpecialtiesSubjectsInRequest(request)) {
                router = new Router(PagePath.REGISTER);
            } else {
                router = new Router(PagePath.ERROR);
                logger.log(Level.ERROR, "Impossible find faculties, specialties,subjects");
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}
