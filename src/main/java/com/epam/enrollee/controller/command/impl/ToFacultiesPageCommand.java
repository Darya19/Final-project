package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.CommandException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ToFacultiesPageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        Router router;
        FacultyServiceImpl facultyService = new FacultyServiceImpl();
        try {
            List<Faculty> faculties = facultyService.findAll();
            request.setAttribute(RequestParameters.FACULTIES, faculties);
            router = new Router(PagePath.FACULTIES);
        } catch (ServiceException e) {
           router = new Router(PagePath.ERROR_500);
        }
        return router;
    }
}
