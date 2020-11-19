package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.router.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Logout command.
 * The command deletes the current user session with all his data and
 * redirects to the index page.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class LogoutCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePath.INDEX);
        request.getSession().invalidate();
        return router;
    }
}