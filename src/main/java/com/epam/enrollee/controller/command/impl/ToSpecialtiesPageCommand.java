package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.exception.CommandException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class ToSpecialtiesPageCommand implements Command {

    private static final String SPECIALTIES = "specialties";
    private static final String FACULTY = "faculty";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        FacultyServiceImpl facultyService = new FacultyServiceImpl();
        String page;
        String facultyId = request.getParameter(RequestParameters.FACULTY_ID);
        try {
                Optional<Faculty> faculty = facultyService.findEnrolleeFaculty
                        (Integer.parseInt(facultyId));
                List<Specialty> specialties = specialtyService.findSpecialtiesOfFaculty
                        (Integer.parseInt(facultyId));
                request.setAttribute(SPECIALTIES, specialties);
                request.setAttribute(FACULTY, faculty.get());
                page = PagePath.SPECIALTIES;
        } catch (ServiceException e) {
           page = PagePath.ERROR_500;
        }
return page;
    }
}
