package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class DeleteSpecialtyCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        HttpSession session = request.getSession();
        Router router;
        String specialtyId = request.getParameter(RequestParameters.SPECIALTY_ID);
        String facultyId = (String) session.getAttribute(RequestParameters.FACULTY_ID);
        try {
            if (specialtyService.checkApplications(specialtyId)) {
                request.setAttribute(RequestParameters.HAS_APPLICATION, true);
                List<Specialty> specialties = specialtyService.findActiveSpecialtiesOfFaculty(facultyId);
                request.setAttribute(RequestParameters.SPECIALTIES, specialties);
                router = new Router(PagePath.ADMIN_SPECIALTIES);
            } else {
                if (specialtyService.remove(specialtyId)) {
                    List<Specialty> specialties = specialtyService.findActiveSpecialtiesOfFaculty(facultyId);
                    request.setAttribute(RequestParameters.SPECIALTIES, specialties);
                    router = new Router(PagePath.ADMIN_SPECIALTIES);
                } else {
                    router = new Router(PagePath.ERROR_500);
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
