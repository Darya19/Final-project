package com.epam.enrollee.controller.command.impl.page;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.EnrolleeMarkRegisterService;
import com.epam.enrollee.model.service.EnrolleeService;
import com.epam.enrollee.model.service.SpecialtyService;
import com.epam.enrollee.model.service.impl.EnrolleeMarkRegisterServiceImpl;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * The type To applications page command.
 * Command to go to the applications page. Command used by user with role admin.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class ToApplicationsPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeService enrolleeService = EnrolleeServiceImpl.getInstance();
        SpecialtyService specialtyService = SpecialtyServiceImpl.getInstance();
        EnrolleeMarkRegisterService registerService = EnrolleeMarkRegisterServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Map<EnrolleeMarkRegister, Enrollee> enrolleesMap = new TreeMap<>(Collections.reverseOrder());
        Router router;
        String specialtyId = request.getParameter(RequestParameter.SPECIALTY_ID);
        try {
            session.setAttribute(AttributeName.PAGE_NUMBER, "1");
            Optional<Specialty> specialty = specialtyService.findSpecialtyById(specialtyId);
            specialty.ifPresent(value -> session.setAttribute(AttributeName.SPECIALTY, value));
            List<Enrollee> enrollees = enrolleeService.findAllUnarchivedEnrolleesOnSpecialty(specialtyId);
            for (Enrollee enrollee : enrollees) {
                int enrolleeId = enrollee.getUserId();
                Optional<EnrolleeMarkRegister> register = registerService.findEnrolleeMarkRegister(enrolleeId);
                register.ifPresent(r -> enrolleesMap.put(r, enrollee));
            }
            session.setAttribute(AttributeName.ENROLLEES, enrolleesMap);
            router = new Router(PagePath.APPLICATIONS);
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}