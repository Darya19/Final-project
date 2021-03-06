package com.epam.enrollee.controller.command.impl.page;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.SpecialtyService;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The type To admin specialties page command.
 * Command to go to the admin specialties page. Command used by user with role admin.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class ToAdminSpecialtiesPageCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        SpecialtyService specialtyService = SpecialtyServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Router router;
        String facultyId = request.getParameter(RequestParameter.FACULTY_ID);
        try {
            if (session.getAttribute(AttributeName.SPECIALTY) != null) {
                session.removeAttribute(AttributeName.SPECIALTY);
            }
            List<Specialty> specialties = specialtyService.findActiveSpecialtiesOfFaculty(facultyId);
            request.setAttribute(AttributeName.SPECIALTIES, specialties);
            session.setAttribute(AttributeName.FACULTY_ID, facultyId);
            router = new Router(PagePath.ADMIN_SPECIALTIES);
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}