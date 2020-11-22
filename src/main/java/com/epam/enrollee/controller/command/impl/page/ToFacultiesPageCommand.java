package com.epam.enrollee.controller.command.impl.page;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.router.Router;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

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
        Router router = new Router(PagePath.FACULTIES);
        return router;
    }
}