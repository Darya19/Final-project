package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.CommandException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class ToEditProfilePageCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        HttpSession session;
        Router router;
        String editPart = request.getParameter(RequestParameters.EDIT_PART);
        request.setAttribute(RequestParameters.EDIT, editPart);
        try {
                session = request.getSession();
                Enrollee enrollee = (Enrollee) session.getAttribute(RequestParameters.ENROLLEE);
                int facultyId = enrollee.getChosenFacultyId();
                List<Specialty> specialties = specialtyService.findSpecialtiesOfFaculty(facultyId);
                request.setAttribute(RequestParameters.SPECIALTIES, specialties);
            router = new Router(PagePath.EDIT_PROFILE);
        }catch (ServiceException e) {
               router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
            }
        return router;
    }
}
