package com.epam.enrollee.controller.command;

import com.epam.enrollee.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    String execute(HttpServletRequest request) throws CommandException;

}
