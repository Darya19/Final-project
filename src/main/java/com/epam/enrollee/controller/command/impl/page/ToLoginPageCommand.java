package com.epam.enrollee.controller.command.impl.page;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The type To login page command.
 * Command to go to the login page. Command used by user with role guest.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class ToLoginPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute(AttributeName.PARAMETERS) != null) {
            session.removeAttribute(AttributeName.PARAMETERS);
        }
        Router router = new Router(PagePath.LOGIN);
        return router;
    }
}