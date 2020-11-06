package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.service.impl.EnrolleeMarkRegisterServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.filter.LevelRangeFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EditMarksCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeMarkRegisterServiceImpl registerService = new EnrolleeMarkRegisterServiceImpl();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session = request.getSession();
        Router router = null;
        EnrolleeMarkRegister register;
        register = (EnrolleeMarkRegister) session.getAttribute(RequestParameters.REGISTER);
        Enrollee enrollee = (Enrollee) session.getAttribute(RequestParameters.ENROLLEE);
        for (
                Subject subject : register.getTestsSubjectsAndMarks().keySet()) {
            String key = String.valueOf(subject.getSubjectId());
            parameters.put(key, request.getParameter(key));
        }
        parameters = registerService.checkMarks(parameters);
        if (parameters.containsValue("")) {
            request.setAttribute(RequestParameters.PARAMETERS, parameters);
            router = new Router(Router.Type.REDIRECT,PagePath.EDIT_PROFILE);
            logger.log(Level.INFO, "Incorrect input data while editing marks");
        } else {
            try {
                Optional<EnrolleeMarkRegister> markRegister = registerService
                        .updateEnrolleRegister(enrollee.getUserId(), parameters);
                if (markRegister.isPresent()) {
                    register = markRegister.get();
                    register = registerService.calculateEnrolleeAverageMark(register);
                    router = new Router(Router.Type.REDIRECT, PagePath.PROFILE);
                }
                session.setAttribute(RequestParameters.REGISTER, register);
            } catch (ServiceException e) {
               router = new Router(PagePath.ERROR_500);
               logger.log(Level.ERROR, "Application error: ", e);
            }
        }
        return router;
    }
}
