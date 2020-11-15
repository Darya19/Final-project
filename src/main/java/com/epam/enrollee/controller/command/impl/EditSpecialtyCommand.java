package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import com.epam.enrollee.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EditSpecialtyCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        SpecialtyServiceImpl specialtyService = SpecialtyServiceImpl.getInstance();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session = request.getSession();
        Router router;
        String specialtyId = request.getParameter(RequestParameter.SPECIALTY_ID);
        String facultyId = (String) session.getAttribute(AttributeName.FACULTY_ID);
        parameters.put(MapKeys.SPECIALTY_ID, specialtyId);
        parameters.put(MapKeys.SPECIALTY_NAME, request.getParameter(RequestParameter.SPECIALTY_NAME));
        parameters.put(MapKeys.NUMBER_OF_SEATS, request.getParameter(RequestParameter.NUMBER_OF_SEATS));
        try {
            Map<String, String> checkedParameters = specialtyService.checkParameters(parameters);
            if (!checkedParameters.get(MapKeys.SPECIALTY_ID).equals(EMPTY_VALUE)) {
                if (checkedParameters.containsValue(EMPTY_VALUE)) {
                    request.setAttribute(AttributeName.PARAMETERS, checkedParameters);
                    Optional<Specialty> specialty = specialtyService.findSpecialtyById(specialtyId);
                    if (specialty.isPresent()) {
                        request.setAttribute(AttributeName.SPECIALTY, specialty.get());
                        router = new Router(PagePath.EDIT_SPECIALTY);
                    } else {
                        router = new Router(PagePath.ERROR);
                        logger.log(Level.ERROR, "Impossible find chosen specialty.");
                    }
                } else {
                    if (specialtyService.update(checkedParameters)) {
                        List<Specialty> specialties = specialtyService.findActiveSpecialtiesOfFaculty(facultyId);
                        request.setAttribute(AttributeName.SPECIALTIES, specialties);
                        router = new Router(PagePath.ADMIN_SPECIALTIES);
                    } else {
                        router = new Router(PagePath.ERROR);
                        logger.log(Level.ERROR, "impossible update faculty");
                    }
                }
            } else {
                router = new Router(PagePath.ERROR);
                logger.log(Level.ERROR, "Incorrect specialty id.");
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.INFO, "Application error: ", e);
        }
        return router;
    }
}
