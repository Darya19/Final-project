package com.epam.enrollee.controller.command;

import com.epam.enrollee.controller.router.Router;

import javax.servlet.http.HttpServletRequest;

public interface Command {

    Router execute(HttpServletRequest request);

}
