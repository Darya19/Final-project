package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.exception.CommandException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.entity.Subject;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import com.epam.enrollee.model.service.impl.SubjectServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


public class ToRegisterPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page = null;
        HttpSession session = request.getSession();
        FacultyServiceImpl facultyService = new FacultyServiceImpl();
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        SubjectServiceImpl subjectService = new SubjectServiceImpl();
        try {
            Optional<List<Faculty>> faculties = facultyService.findAll();
            Optional<List<Specialty>> specialties = specialtyService.findAll();
            Optional<List<Subject>> subjects = subjectService.findAll();
            if (faculties.isPresent() && specialties.isPresent() && subjects.isPresent()) {
                session.setAttribute(RequestParameters.FACULTIES, faculties.get());
                session.setAttribute(RequestParameters.SPECIALTIES, specialties.get());
                session.setAttribute(RequestParameters.SUBJECTS, subjects.get());
                page = PagePath.REGISTER;
            } else {
                page = PagePath.ERROR_404;
            }
        } catch (ServiceException e) {
            page = PagePath.ERROR_404;
        }
        return page;
    }
}
