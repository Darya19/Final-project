package com.epam.task1.command.impl;

import com.epam.task1.command.Command;
import com.epam.task1.model.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

import static com.epam.task1.command.PagePath.LOGIN;
import static com.epam.task1.command.PagePath.MAIN;

public class LoginCommand implements Command {

    private static final String PARAM_NAME_LOGIN = "login";
    private static final String PARAM_NAME_PASSWORD = "password";

    @Override
    public String execute(HttpServletRequest request) {
        UserServiceImpl service = new UserServiceImpl();
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        if (service.checkLogin(login, pass)) {
            request.setAttribute("user", login);
            page = MAIN;
        } else {
            request.setAttribute("errorLoginMessage", "incorrect login or password");
            page = LOGIN;
        }
        return page;
    }
}
