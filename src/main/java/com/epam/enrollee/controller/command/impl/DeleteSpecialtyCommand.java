package com.epam.enrollee.controller.command.impl;

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
 * The type Delete specialty command.
 * Delete a specialty, the specialty id come with request from user.
 * The command can be used by user with role admin. If the specialty is deleted successfully,
 * the specialty don't displays at the page specialties. The specialty is not deleted from
 * the database,but is marked with a status delete
 *
 *  @author Darya Shcherbina
 *  @version 1.0
 */
public class DeleteSpecialtyCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        SpecialtyService specialtyService = SpecialtyServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Router router;
        String specialtyId = request.getParameter(RequestParameter.SPECIALTY_ID);
        String facultyId = (String) session.getAttribute(AttributeName.FACULTY_ID);
        try {
            if (specialtyService.checkConsideredApplications(specialtyId)) {
                request.setAttribute(AttributeName.HAS_APPLICATION, true);
                List<Specialty> specialties = specialtyService.findActiveSpecialtiesOfFaculty(facultyId);
                request.setAttribute(AttributeName.SPECIALTIES, specialties);
                router = new Router(PagePath.ADMIN_SPECIALTIES);
            } else {
                if (specialtyService.remove(specialtyId)) {
                    List<Specialty> specialties = specialtyService.findActiveSpecialtiesOfFaculty(facultyId);
                    request.setAttribute(AttributeName.SPECIALTIES, specialties);
                    router = new Router(PagePath.ADMIN_SPECIALTIES);
                } else {
                    router = new Router(PagePath.ERROR);
                    logger.log(Level.ERROR, "Impossible delete specialty ");
                }
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error: ", e);
        }
        return router;
    }
}
