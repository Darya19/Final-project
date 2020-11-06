package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.exception.CommandException;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class DeleteFacultyCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        FacultyServiceImpl facultyService = new FacultyServiceImpl();
        String page = null;
        String stringFacultyId = request.getParameter(RequestParameters.FACULTY_ID);
        int facultyId = Integer.parseInt(stringFacultyId);
        boolean hasApplication;
        boolean isDeleted;
        try {
            hasApplication = facultyService.checkFacultyAplications(facultyId);
            if (hasApplication) {
                request.setAttribute(RequestParameters.HAS_APPLICATION, hasApplication);
                List<Faculty> faculties = facultyService.findAll();
                request.setAttribute(RequestParameters.FACULTIES, faculties);
                page = PagePath.ADMIN_FACULTIES;
            } else {
                if (facultyService.remove(facultyId)) {
                    List<Faculty> faculties = facultyService.findAll();
                    request.setAttribute(RequestParameters.FACULTIES, faculties);
                    page = PagePath.ADMIN_FACULTIES;
                }
            }
        } catch (ServiceException e) {
            page = PagePath.ERROR_500;
        }
        return page;
    }
}
