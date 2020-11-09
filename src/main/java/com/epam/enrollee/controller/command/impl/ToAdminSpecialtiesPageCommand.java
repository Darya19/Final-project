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
import java.util.List;

public class ToAdminSpecialtiesPageCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        Router router;
        try {
            String facultyId = request.getParameter(RequestParameters.FACULTY_ID);
            int intFacultyId = Integer.parseInt(facultyId);
            List<Specialty> specialties = specialtyService.findAllSpecialtiesOfFaculty(intFacultyId);
            request.setAttribute(RequestParameters.SPECIALTIES, specialties);
            router = new Router(PagePath.ADMIN_SPECIALTIES);
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}
