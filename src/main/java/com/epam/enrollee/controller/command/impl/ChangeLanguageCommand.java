package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
