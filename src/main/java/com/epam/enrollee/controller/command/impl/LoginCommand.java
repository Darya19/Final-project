package com.epam.enrollee.controller.command.impl;


import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.model.service.impl.UserServiceImpl;
import com.google.protobuf.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.epam.enrollee.controller.command.PagePath.*;


public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        UserServiceImpl service = new UserServiceImpl();
        String page = null;
        String email = request.getParameter(RequestParameters.EMAIL);
        String pass = request.getParameter(RequestParameters.PASSWORD);
        try {
            if (service.checkEmailAndPassword(email, pass)) {
                request.setAttribute("user", email);
                HttpSession session = request.getSession(true);
                session.setAttribute("status", );
                page = PROFILE;
            } else {
                request.setAttribute("errorLoginMessage", "incorrect login or password");
                page = LOGIN;
            }
        } catch (ServiceException e) {
            page = ERROR_404;
        }
        return page;
        /*TODO неправильный логин возвращаем туже страницу
        правильные: переход на страницу в зависимости от роли с использованием ифа, далее проверяется статус
         почта многопоточку не использовать*/
    }
}
