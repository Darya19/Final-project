package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.model.type.ApplicationStatus;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class ChangeApplicationStatusCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeServiceImpl enrolleeService = EnrolleeServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Router router;
        String enrolleeId = request.getParameter(RequestParameter.ENROLLEE_ID);
        String status = request.getParameter(RequestParameter.STATUS);
        Specialty specialty = (Specialty) session.getAttribute(RequestParameter.SPECIALTY);
        try {
            if (enrolleeService.changeApplicationStatus(enrolleeId, status, specialty)) {
                int intEnrolleeId = Integer.parseInt(enrolleeId);
                Map<EnrolleeMarkRegister, Enrollee> enrollees = (Map<EnrolleeMarkRegister, Enrollee>) session.getAttribute
                        (RequestParameter.ENROLLEES);
                for (Enrollee enrollee : enrollees.values()) {
                    if (enrollee.getUserId() == intEnrolleeId) {
                        enrollee.setApplicationStatus(ApplicationStatus.valueOf(status.toUpperCase()));
                    }
                }
            } else {
                request.setAttribute(RequestParameter.IS_CHANGED, false);
            }
            router = new Router(PagePath.APPLICATIONS);
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error: ", e);
        }
        return router;
    }
}
