package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.router.Router;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.epam.enrollee.controller.command.PagePath.ERROR_404;

/**
 * The type Empty command.
 * If request come from page with empty parameter command. Servlet call Empty command.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class EmptyCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(ERROR_404);
        logger.log(Level.ERROR, "Command is empty");
        return router;
    }
}
