package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class PaginationCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        String pageNumber = request.getParameter(RequestParameter.PAGE_NUMBER);
        HttpSession session = request.getSession();
        session.removeAttribute(AttributeName.PAGE_NUMBER);
        session.setAttribute(AttributeName.PAGE_NUMBER, pageNumber);
        router = new Router(PagePath.APPLICATIONS);
        return router;
    }
}
