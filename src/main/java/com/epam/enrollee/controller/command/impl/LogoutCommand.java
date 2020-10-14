package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

import static com.epam.enrollee.controller.command.PagePath.INDEX;

public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = INDEX;
        request.getSession().invalidate();
        return page;
    }
}