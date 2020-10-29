package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.enrollee.controller.command.PagePath.MAIN;

public class ProfileCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        HttpSession session;
        String page = null;
        session = request.getSession();
return MAIN;
    }
}