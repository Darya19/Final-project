package com.epam.enrollee.controller.command.impl.page;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ToEditProfilePageCommand implements Command {

    private static Logger logger = LogManager.getLogger();
    private static final String EDIT_SPECIALTY = "edit_specialty";

    @Override
    public Router execute(HttpServletRequest request) {
        SpecialtyServiceImpl specialtyService = SpecialtyServiceImpl.getInstance();
        HttpSession  session = request.getSession();
        Router router;
        String editPart = request.getParameter(RequestParameter.EDIT_PART);
        request.setAttribute(RequestParameter.EDIT_PART, editPart);
        try {
            if (editPart.equals(EDIT_SPECIALTY)) {
                Enrollee enrollee = (Enrollee) session.getAttribute(RequestParameter.ENROLLEE);
                int facultyId = enrollee.getChosenFacultyId();
                List<Specialty> specialties = specialtyService.findOpenSpecialtiesOfFaculty(String.valueOf(facultyId));
                request.setAttribute(RequestParameter.SPECIALTIES, specialties);
            }
            router = new Router(PagePath.EDIT_PROFILE);
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}
