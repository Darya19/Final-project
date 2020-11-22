package com.epam.enrollee.controller.command.impl.page;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.FacultyService;
import com.epam.enrollee.model.service.SpecialtyService;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * The type To specialties page command.
 * Command to go to the specialties page. Command used by user with role user, admin, guest.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class ToSpecialtiesPageCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        SpecialtyService specialtyService = SpecialtyServiceImpl.getInstance();
        FacultyService facultyService = FacultyServiceImpl.getInstance();
        Router router;
        String facultyId = request.getParameter(RequestParameter.FACULTY_ID);
        try {
            Optional<Faculty> faculty = facultyService.findFacultyById(facultyId);
            List<Specialty> specialties = specialtyService.findActiveSpecialtiesOfFaculty(facultyId);
            if (faculty.isPresent() && !specialties.isEmpty()) {
                request.setAttribute(AttributeName.SPECIALTIES, specialties);
                request.setAttribute(AttributeName.FACULTY, faculty.get());
                router = new Router(PagePath.SPECIALTIES);
            } else {
                router = new Router(PagePath.ERROR);
                logger.log(Level.ERROR, "Impossible find faculty or specialty in db");
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}