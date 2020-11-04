package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;

import javax.servlet.http.HttpServletRequest;

import static com.epam.enrollee.controller.command.PagePath.INDEX;

public class LogoutCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String page = PagePath.INDEX;
        request.getSession().invalidate();
        return page;
    }
}
