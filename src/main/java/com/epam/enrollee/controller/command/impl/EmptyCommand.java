package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

import static com.epam.enrollee.controller.command.PagePath.ERROR_404;
import static com.epam.enrollee.controller.command.PagePath.LOGIN;

public class EmptyCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = ERROR_404;
        return page;
    }
}
