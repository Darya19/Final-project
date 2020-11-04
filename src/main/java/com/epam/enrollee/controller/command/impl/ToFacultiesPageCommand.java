package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.exception.CommandException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ToFacultiesPageCommand implements Command {

    private static final String FACULTIES = "faculties";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String page;
        FacultyServiceImpl facultyService = new FacultyServiceImpl();
        try {
            List<Faculty> faculties = facultyService.findAll();
            request.setAttribute(FACULTIES, faculties);
            page = PagePath.FACULTIES;
        } catch (ServiceException e) {
            page = PagePath.ERROR_500;
        }
        return page;
    }
}
