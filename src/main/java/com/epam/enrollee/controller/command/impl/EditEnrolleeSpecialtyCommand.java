package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.EnrolleeService;
import com.epam.enrollee.model.service.SpecialtyService;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * The type Edit enrollee specialty command.
 * The specialty id come with request from user. The command can be used by user with role user.
 * The enrollee can change the specialty at the chosen faculty if the status of his application
 * is being considered. If enrollee specialty edition passed successfully, His application is
 * displayed in the list of the selected specialty. And the admin can accept or reject it.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class EditEnrolleeSpecialtyCommand implements Command {

    private static Logger logger = LogManager.getLogger();
    private static final String EDIT_SPECIALTY = "edit_specialty";

    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeService enrolleeService = EnrolleeServiceImpl.getInstance();
        SpecialtyService specialtyService = SpecialtyServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Router router;
        boolean isUpdated;
        Enrollee enrollee = (Enrollee) session.getAttribute(AttributeName.ENROLLEE);
        String specialtyId = request.getParameter(RequestParameter.SPECIALTY_ID);
        try {
            if (specialtyId.equals(EMPTY_VALUE)) {
                router = new Router(Router.Type.REDIRECT, PagePath.EDIT_PROFILE);
            } else {
                if (session.getAttribute(AttributeName.EDIT_PART) != null) {
                    session.removeAttribute(AttributeName.EDIT_PART);
                    session.removeAttribute(AttributeName.SPECIALTIES);
                }
                isUpdated = enrolleeService.updateEnrolleeSpecialty
                        (enrollee, specialtyId);
                Optional<Specialty> optionalSpecialty = specialtyService
                        .findSpecialtyById(specialtyId);
                if (isUpdated && optionalSpecialty.isPresent()) {
                    session.removeAttribute(AttributeName.SPECIALTY);
                    session.setAttribute(AttributeName.SPECIALTY, optionalSpecialty.get());
                    router = new Router(Router.Type.REDIRECT, PagePath.PROFILE);
                } else {
                    router = new Router(Router.Type.REDIRECT, PagePath.ERROR);
                    logger.log(Level.ERROR, "Impossible add updated enrollee specialty in db");
                }
            }
        } catch (ServiceException e) {
            router = new Router(Router.Type.REDIRECT, PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error: ", e);
        }
        return router;
    }
}