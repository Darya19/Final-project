package com.epam.enrollee.controller.command.impl.pagecommand;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.router.Router;

import javax.servlet.http.HttpServletRequest;

public class ToProfilePageCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
       return new Router(PagePath.PROFILE);
    }
}
