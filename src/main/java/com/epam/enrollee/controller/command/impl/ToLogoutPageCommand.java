package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public class ToLogoutPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = PagePath.LOGOUT;
        return null;
    }
}
