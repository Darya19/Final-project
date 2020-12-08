package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.service.EnrolleeMarkRegisterService;
import com.epam.enrollee.model.service.impl.EnrolleeMarkRegisterServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The type Edit marks command.
 * Marks values and specialties id come with request from user. The command can be used by
 * user with role user. The enrollee can change marks value (without changing subjects)
 * if the status of his application is being considered. If enrollee marks edition passed
 * successfully, his application displays in the list of applications with calculated average mark.
 * And the admin can accept or reject it. If the data is not entered correctly, the user is returned
 * to the edit profile page.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class EditMarksCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeMarkRegisterService registerService = EnrolleeMarkRegisterServiceImpl.getInstance();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session = request.getSession();
        Router router;
        EnrolleeMarkRegister register;
        register = (EnrolleeMarkRegister) session.getAttribute(AttributeName.REGISTER);
        Enrollee enrollee = (Enrollee) session.getAttribute(AttributeName.ENROLLEE);
        for (Subject subject : register.getTestsSubjectsAndMarks().keySet()) {
            String key = String.valueOf(subject.getSubjectId());
            parameters.put(key, request.getParameter(key));
        }
        try {
            parameters = registerService.checkParameters(parameters);
            if (parameters.containsValue(EMPTY_VALUE)) {
                session.setAttribute(AttributeName.INCORRECT, true);
                router = new Router(Router.Type.REDIRECT, PagePath.EDIT_PROFILE);
            } else {
                if (session.getAttribute(AttributeName.INCORRECT) != null) {
                    session.removeAttribute(AttributeName.INCORRECT);
                }
                session.removeAttribute(AttributeName.EDIT_PART);
                Optional<EnrolleeMarkRegister> markRegister = registerService
                        .updateEnrolleeMarks(enrollee.getUserId(), parameters);
                if (markRegister.isPresent()) {
                    register = markRegister.get();
                    session.removeAttribute(AttributeName.REGISTER);
                    session.setAttribute(AttributeName.REGISTER, register);
                    router = new Router(Router.Type.REDIRECT, PagePath.PROFILE);
                } else {
                    router = new Router(Router.Type.REDIRECT, PagePath.ERROR);
                    logger.log(Level.ERROR, "Impossible add updated marks in db");
                }
            }
        } catch (ServiceException e) {
            router = new Router(Router.Type.REDIRECT, PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error: ", e);
        }
        return router;
    }
}