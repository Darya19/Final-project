package com.epam.enrollee.controller.command.impl.pagecommand;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameters;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ToEditSpecialtyPageCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        Router router;
        String specialtyId = request.getParameter(RequestParameters.SPECIALTY_ID);
        try {
            if (specialtyId != null) {
                Optional<Specialty> specialty = specialtyService.findSpecialtyById(specialtyId);
                if (specialty.isPresent()) {
                    request.setAttribute(RequestParameters.SPECIALTY, specialty.get());
                    router = new Router(PagePath.EDIT_SPECIALTY);
                } else {
                    router = new Router(PagePath.ERROR_500);
                    logger.log(Level.ERROR, "Impossible find chosen faculty.");
                }
            } else {
                router = new Router(PagePath.EDIT_SPECIALTY);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}
