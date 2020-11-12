package com.epam.enrollee.controller.command.impl.page;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.impl.EnrolleeMarkRegisterServiceImpl;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class ToApplicationsPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeServiceImpl enrolleeService = new EnrolleeServiceImpl();
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        EnrolleeMarkRegisterServiceImpl registerService = new EnrolleeMarkRegisterServiceImpl();
        HttpSession session = request.getSession();
        Map<EnrolleeMarkRegister, Enrollee> enrolleesMap = new TreeMap<>(Collections.reverseOrder());
        Router router;
        String specialtyId = request.getParameter(RequestParameter.SPECIALTY_ID);
        try {
            List<Enrollee> enrollees = enrolleeService.findAllEnrolleesOnSpecialty(specialtyId);
            for (Enrollee enrollee : enrollees) {
                int enrolleeId = enrollee.getUserId();
                Optional<EnrolleeMarkRegister> register = registerService.findEnrolleeMarkRegister(enrolleeId);
                if (register.isPresent()) {
                    enrolleesMap.put(register.get(), enrollee);
                } else {
                    router = new Router(PagePath.ERROR_500);
                    logger.log(Level.ERROR, "Impossible find enrollee parameters");
                }
               Optional<Specialty> specialty = specialtyService.findSpecialtyById(specialtyId);
                if(specialty.isPresent()){
                    session.setAttribute(RequestParameter.SPECIALTY, specialty.get());
                }
            }
            session.setAttribute(RequestParameter.ENROLLEES, enrolleesMap);
            router = new Router(PagePath.APPLICATIONS);
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}
