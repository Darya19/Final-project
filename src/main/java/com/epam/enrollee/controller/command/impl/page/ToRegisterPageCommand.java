package com.epam.enrollee.controller.command.impl.page;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * The type To register page command.
 * Command to go to the register page. Command used by user with role guest.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class ToRegisterPageCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Router router;
        try {
            if (session.getAttribute(AttributeName.PARAMETERS) != null) {
                session.removeAttribute(AttributeName.PARAMETERS);
            }
            if (putFacultiesSpecialtiesSubjectsInSession(session)) {
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