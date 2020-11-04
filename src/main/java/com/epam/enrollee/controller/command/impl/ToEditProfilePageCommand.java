package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.exception.CommandException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class ToEditProfilePageCommand implements Command {
    private static final String EDIT = "edit";
    private static final String EDIT_SPECIALTY = "edit_specialty";
    private static final String EDIT_PERSONAL_INFORMATION = "edit_personal_information";
    private static final String ENROLLEE ="enrollee";
    private static final String SPECIALTIES ="specialties";
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        HttpSession session;
        String page;
        String editPart = request.getParameter(RequestParameters.EDIT_PART);
        request.setAttribute(EDIT, editPart);
        try {
            if (editPart.equals(EDIT_SPECIALTY)) {
                session = request.getSession();
                Enrollee enrollee = (Enrollee) session.getAttribute(ENROLLEE);
                int facultyId = enrollee.getChosenFacultyId();
                List<Specialty> specialties = specialtyService.findSpecialtiesOfFaculty(facultyId);
                request.setAttribute(SPECIALTIES, specialties);
            }
            page = PagePath.EDIT_PROFILE;
        }catch (ServiceException e) {
               page = PagePath.ERROR_500;
            }
        return page;
    }
    //TODO for marks
}
