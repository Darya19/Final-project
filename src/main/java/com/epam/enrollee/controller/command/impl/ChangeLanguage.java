package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ChangeLanguage implements Command {

    private static final String ENGLISH = "en";
    private static final String RUSSIAN = "";

    @Override
    public Router execute(HttpServletRequest request) {
        String language = request.getParameter(RequestParameters.LOCALE);
        HttpSession session = request.getSession();
        if (language.equals(ENGLISH)) {
            session.setAttribute(RequestParameters.LOCALE, RUSSIAN);
        } else {
            session.setAttribute(RequestParameters.LOCALE, ENGLISH);
        }
        return null;
    }
}
