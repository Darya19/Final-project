package com.epam.enrollee.controller.command.impl.pagecommand;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public class ToSpecialtiesPageCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        FacultyServiceImpl facultyService = new FacultyServiceImpl();
        Router router;
        String facultyId = request.getParameter(RequestParameters.FACULTY_ID);
        try {
            Optional<Faculty> faculty = facultyService.findFacultyById
                    (facultyId);
            List<Specialty> specialties = specialtyService.findOpenSpecialtiesOfFaculty
                    (facultyId);
            if (faculty.isPresent() && !specialties.isEmpty()) {
                request.setAttribute(RequestParameters.SPECIALTIES, specialties);
                request.setAttribute(RequestParameters.FACULTY, faculty.get());
                router = new Router(PagePath.SPECIALTIES);
            } else {
                router = new Router(PagePath.ERROR_500);
                logger.log(Level.ERROR, "Impossible find faculty or specialty in db");
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}
