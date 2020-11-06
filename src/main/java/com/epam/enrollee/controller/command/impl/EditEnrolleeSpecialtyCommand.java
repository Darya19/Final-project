package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.exception.CommandException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.impl.EnrolleeMarkRegisterServiceImpl;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

public class EditEnrolleeSpecialtyCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        EnrolleeServiceImpl enrolleeService = new EnrolleeServiceImpl();
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        HttpSession session = request.getSession();
        String page = null;
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
                page = PagePath.PROFILE;
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return page;
    }
}
