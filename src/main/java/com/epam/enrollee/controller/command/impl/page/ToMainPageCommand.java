package com.epam.enrollee.controller.command.impl.page;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.model.type.RoleType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The type To main page command.
 * Command to go to the main page. Command used by user with role user, admin, guest.
 *
 *  @author Darya Shcherbina
 *  @version 1.0
 */
public class ToMainPageCommand implements Command {

    private static final String ENGLISH_LANGUAGE = "en";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        if (session.getAttribute(AttributeName.LOCALE) == null) {
            session.setAttribute(AttributeName.LOCALE, ENGLISH_LANGUAGE);
            session.setAttribute(AttributeName.ROLE, RoleType.GUEST);
        }
        router = new Router(PagePath.MAIN);
        return router;
    }
}