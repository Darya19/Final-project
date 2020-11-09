package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.model.type.RoleType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ToMainPageCommand implements Command {

    private static final String ENGLISH_LANGUAGE = "en";

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        HttpSession session = request.getSession();
        if(session.getAttribute(RequestParameters.LOCALE) == null){
        session.setAttribute(RequestParameters.LOCALE, ENGLISH_LANGUAGE);
        session.setAttribute(RequestParameters.ROLE, RoleType.GUEST);}
        router = new Router(PagePath.MAIN);
        return router;
    }
}






