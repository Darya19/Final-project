package com.epam.enrollee.controller.command.impl.page;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.router.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The type To login page command.
 * Command to go to the login page. Command used by user with role guest.
 *
 *  @author Darya Shcherbina
 *  @version 1.0
 */
public class ToLoginPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePath.LOGIN);
        return router;
    }
}