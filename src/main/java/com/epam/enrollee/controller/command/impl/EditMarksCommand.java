package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.exception.CommandException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.service.impl.EnrolleeMarkRegisterServiceImpl;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EditMarksCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        EnrolleeMarkRegisterServiceImpl registerService = new EnrolleeMarkRegisterServiceImpl();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session = request.getSession();
        String page = null;
        String firstName = null;
        String specialtyId = null;
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
            page = PagePath.EDIT_PROFILE;
        } else {
            try {
                Optional<EnrolleeMarkRegister> markRegister = registerService
                        .updateEnrolleRegister(enrollee.getUserId(), parameters);
                if (markRegister.isPresent()) {
                    register = markRegister.get();
                    register = registerService.calculateEnrolleeAverageMark(register);
                    page = PagePath.PROFILE;
                }
                session.setAttribute(RequestParameters.REGISTER, register);
            } catch (ServiceException e) {
                page = PagePath.ERROR_500;
            }
        }
        return page;
    }
}
