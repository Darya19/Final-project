package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class EditEnrolleeSpecialtyCommand implements Command {

    private static final String EDIT_SPECIALTY = "edit_specialty";
    private static final String EMPTY_STRING = "";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeServiceImpl enrolleeService = EnrolleeServiceImpl.getInstance();
        SpecialtyServiceImpl specialtyService = SpecialtyServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Router router;
        Enrollee enrollee = (Enrollee) session.getAttribute(RequestParameter.ENROLLEE);
        Optional<Enrollee> newEnrollee;
        String specialtyId = request.getParameter(RequestParameter.SPECIALTY_ID);
        try {
            if (specialtyId.equals(EMPTY_STRING)) {
                request.setAttribute(RequestParameter.EDIT_PART, EDIT_SPECIALTY);
                int facultyId = enrollee.getChosenFacultyId();
                List<Specialty> specialties = specialtyService.findOpenSpecialtiesOfFaculty
                        (String.valueOf(facultyId));
                request.setAttribute(RequestParameter.SPECIALTIES, specialties);
                router = new Router(PagePath.EDIT_PROFILE);
            } else {
                newEnrollee = enrolleeService.updateEnrolleeSpecialty
                        (enrollee, specialtyId);
                Optional<Specialty> optionalSpecialty = specialtyService
                        .findSpecialtyById(specialtyId);
                if (newEnrollee.isPresent() && optionalSpecialty.isPresent()) {
                    session.removeAttribute(RequestParameter.SPECIALTY);
                    session.setAttribute(RequestParameter.SPECIALTY, optionalSpecialty.get());
                    router = new Router(PagePath.PROFILE);
                } else {
                    router = new Router(PagePath.ERROR);
                    logger.log(Level.ERROR, "Impossible add updated enrollee specialty in db");
                }
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error: ", e);
        }
        return router;
    }
}
