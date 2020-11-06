package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
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
    
    private static Logger logger = LogManager.getLogger();
    
    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeServiceImpl enrolleeService = new EnrolleeServiceImpl();
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        HttpSession session = request.getSession();
        Router router = null;
        Enrollee enrollee = (Enrollee) session.getAttribute(RequestParameters.ENROLLEE);
        Optional<Enrollee> newEnrollee;
        String specialtyId = request.getParameter(RequestParameters.SPECIALTY_ID);
        try {
            newEnrollee = enrolleeService.updateEnrolleeSpecialty
                    (enrollee, specialtyId);
        if (newEnrollee.isPresent()) {
                session.setAttribute(RequestParameters.ENROLLEE, newEnrollee.get());
                int facultyId = enrollee.getChosenFacultyId();
                List<Specialty> specialties = specialtyService.findSpecialtiesOfFaculty(facultyId);
                request.setAttribute(RequestParameters.SPECIALTIES, specialties);
                router = new Router(Router.Type.REDIRECT, PagePath.PROFILE);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "application error: ", e);
        }
        return router;
    }
}
