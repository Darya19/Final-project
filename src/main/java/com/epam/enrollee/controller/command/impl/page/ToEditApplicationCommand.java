package com.epam.enrollee.controller.command.impl.page;

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
 * The type To edit application command.
 * Command to go to the edit application page. Command used by user with role user.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class ToEditApplicationCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Router router;
        try {
            if (putFacultiesSpecialtiesSubjectsInSession(session)) {
                router = new Router(PagePath.EDIT_APPLICATION);
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