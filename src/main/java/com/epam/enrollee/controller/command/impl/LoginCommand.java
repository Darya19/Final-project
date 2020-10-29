package com.epam.enrollee.controller.command.impl;


import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.entity.User;
import com.epam.enrollee.model.enumtype.RoleType;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import com.epam.enrollee.propertiesreader.MessageManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.epam.enrollee.controller.command.PagePath.*;
import static com.epam.enrollee.controller.command.RequestParameters.ERROR_LOGIN_MESSAGE;


public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        EnrolleeServiceImpl enrolleeService = new EnrolleeServiceImpl();
        FacultyServiceImpl facultyService = new FacultyServiceImpl();
        HttpSession session;
        String page = null;
        String email = request.getParameter(RequestParameters.EMAIL);
        String pass = request.getParameter(RequestParameters.PASSWORD);
        try {
            if (enrolleeService.checkEmailAndPassword(email, pass)) {
                Optional<User> optionalUser = enrolleeService.findUserByEmail(email);
                if (optionalUser.isPresent()) {
                    session = request.getSession(true);
                    if (optionalUser.get().getRole().equals(RoleType.USER)) {
                        Optional<Enrollee> optionalEnrollee = enrolleeService.findEnrolleeByEmail(email);
                        if (optionalEnrollee.isPresent()) {
                            Enrollee enrollee = optionalEnrollee.get();
                            session.setAttribute("enrollee", enrollee);
                            session.setAttribute("previous page", LOGIN);
                            Faculty enrolleeFaculty = facultyService.findFacultyByUserId(enrollee.getUserId()).get();
                            request.setAttribute("faculty", enrolleeFaculty);
                            page = PROFILE;
                        }
                    }
                    if (optionalUser.get().getRole().equals(RoleType.ADMIN)) {
                        User user = optionalUser.get();
                        session.setAttribute("user", user);
                        session.setAttribute("previous page", LOGIN);
                       // session.setAttribute("locale", );
                        page = STATEMENT;
                    }
                } else {
                    page = ERROR_404;
                }
            } else {
                request.setAttribute(ERROR_LOGIN_MESSAGE, MessageManager.getProperty("login.loginerror"));
                page = LOGIN;
            }
        } catch (ServiceException e) {
            page = ERROR_404;
        }
        return page;
        /*TODO неправильный логин возвращаем туже страницу add if status
        правильные: переход на страницу в зависимости от роли с использованием ифа, далее проверяется статус
         почта многопоточку не использовать*/

    }
}
