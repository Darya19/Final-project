package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

import static com.epam.enrollee.controller.command.PagePath.CONTACTS;

public class ToContactsPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        return CONTACTS;
    }
}
