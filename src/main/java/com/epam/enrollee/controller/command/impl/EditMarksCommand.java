package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.service.impl.EnrolleeMarkRegisterServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EditMarksCommand implements Command {

    private static Logger logger = LogManager.getLogger();
    private static final String EDIT_MARKS = "edit_marks";
    private static final String EMPTY_STRING = "";


    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeMarkRegisterServiceImpl registerService = new EnrolleeMarkRegisterServiceImpl();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session = request.getSession();
        Router router;
        EnrolleeMarkRegister register;
        register = (EnrolleeMarkRegister) session.getAttribute(RequestParameters.REGISTER);
        Enrollee enrollee = (Enrollee) session.getAttribute(RequestParameters.ENROLLEE);
        for (Subject subject : register.getTestsSubjectsAndMarks().keySet()) {
            String key = String.valueOf(subject.getSubjectId());
            parameters.put(key, request.getParameter(key));
        }
        parameters = registerService.checkParameters(parameters);
        if (parameters.containsValue(EMPTY_STRING)) {
            request.setAttribute(RequestParameters.PARAMETERS, parameters);
            request.setAttribute(RequestParameters.EDIT_PART, EDIT_MARKS);
            router = new Router(PagePath.EDIT_PROFILE);
        } else {
            try {
                Optional<EnrolleeMarkRegister> markRegister = registerService
                        .updateEnrolleRegister(enrollee.getUserId(), parameters);
                if (markRegister.isPresent()) {
                    register = markRegister.get();
                    session.removeAttribute(RequestParameters.REGISTER);
                    session.setAttribute(RequestParameters.REGISTER, register);
                    router = new Router(PagePath.PROFILE);
                } else {
                    router = new Router(PagePath.ERROR_500);
                    logger.log(Level.ERROR, "Impossible add updated marks in db");
                }
            } catch (ServiceException e) {
                router = new Router(PagePath.ERROR_500);
                logger.log(Level.ERROR, "Application error: ", e);
            }
        }
        return router;
    }
}
