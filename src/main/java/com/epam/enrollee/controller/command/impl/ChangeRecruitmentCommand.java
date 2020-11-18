package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.SpecialtyService;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The type Change recruitment command.
 * The required recruitment type come with request from the user.
 * The command can be used by user with role admin.The recruitment change on close after checking
 * active applications from user. If the specialty haven't applications, updated information displays
 * at specialties page. Enrollee can't send application to the specialty with close recruitment.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class ChangeRecruitmentCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        SpecialtyService specialtyService = SpecialtyServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Router router;
        String specialtyId = request.getParameter(RequestParameter.SPECIALTY_ID);
        String recruitment = request.getParameter(RequestParameter.RECRUITMENT);
        String facultyId = (String) session.getAttribute(AttributeName.FACULTY_ID);
        try {
            if (!specialtyService.checkConsideredApplications(specialtyId)) {
                List<Integer> oldApplications = specialtyService.findAllApplicationsBySpecialty(specialtyId);
                if (specialtyService.changeSpecialtyRecruitment(specialtyId, recruitment, oldApplications)) {
                    List<Specialty> specialties = specialtyService.findActiveSpecialtiesOfFaculty(String.valueOf(facultyId));
                    request.setAttribute(AttributeName.SPECIALTIES, specialties);
                    router = new Router(PagePath.ADMIN_SPECIALTIES);
                } else {
                    router = new Router(PagePath.ERROR);
                    logger.log(Level.ERROR, "Impossible change recruitment");
                }
            } else {
                request.setAttribute(AttributeName.HAS_APPLICATION, true);
                List<Specialty> specialties = specialtyService.findActiveSpecialtiesOfFaculty
                        (facultyId);
                request.setAttribute(AttributeName.SPECIALTIES, specialties);
                router = new Router(PagePath.ADMIN_SPECIALTIES);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error: ", e);
        }
        return router;
    }
}
