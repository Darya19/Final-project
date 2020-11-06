package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.exception.CommandException;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.enrollee.controller.command.PagePath.MAIN;

public class ToMainPageCommand implements Command {

    private static final String ENGLISH_LANGUAGE = "en";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.setAttribute(RequestParameters.LOCALE, ENGLISH_LANGUAGE);
        return MAIN;
    }
}
