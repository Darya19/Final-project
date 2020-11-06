package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.router.Router;

import javax.servlet.http.HttpServletRequest;

import static com.epam.enrollee.controller.command.PagePath.ERROR_404;

public class EmptyCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(ERROR_404);
        return router;
    }
}
