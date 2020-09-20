package com.epam.task1.command.impl;

import com.epam.task1.command.Command;

import javax.servlet.http.HttpServletRequest;

import static com.epam.task1.command.PagePath.INDEX;

public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = INDEX;
        request.getSession().invalidate();
        return page;
    }
}
