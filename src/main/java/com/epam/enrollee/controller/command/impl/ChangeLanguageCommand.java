package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The type Change language command.
 * Change language the user can at any page, Language type come with request from the user.
 * The command can be used by the user with any role. Every changing forwards the
 * user at home page.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class ChangeLanguageCommand implements Command {

    private static final String ENGLISH = "en";
    private static final String RUSSIAN = "";

    @Override
    public Router execute(HttpServletRequest request) {
        String language = request.getParameter(RequestParameter.LOCALE);
        HttpSession session = request.getSession();
        if (language.equals(ENGLISH)) {
            session.setAttribute(AttributeName.LOCALE, RUSSIAN);
        } else {
            session.setAttribute(AttributeName.LOCALE, ENGLISH);
        }
        return new Router(PagePath.MAIN);
    }
}
