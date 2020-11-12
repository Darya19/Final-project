package com.epam.enrollee.controller.command.impl;

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

public class AddSpecialtyCommand implements Command {

    private static final String EMPTY_STRING = "";
    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        SpecialtyServiceImpl specialtyService = new SpecialtyServiceImpl();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session = request.getSession();
        Router router;
        String facultyId = (String) session.getAttribute(RequestParameter.FACULTY_ID);
        parameters.put(MapKeys.SPECIALTY_NAME, request.getParameter(RequestParameter.SPECIALTY_NAME));
        parameters.put(MapKeys.NUMBER_OF_SEATS, request.getParameter(RequestParameter.NUMBER_OF_SEATS));
        parameters.put(MapKeys.FACULTY_ID, facultyId);
        parameters = specialtyService.checkParameters(parameters);
        if (parameters.containsValue(EMPTY_STRING)) {
            request.setAttribute(RequestParameter.PARAMETERS, parameters);
            router = new Router(PagePath.EDIT_SPECIALTY);
        } else {
            try {
                if (specialtyService.create(parameters)) {
                    List<Specialty> specialties = specialtyService.findActiveSpecialtiesOfFaculty
                            (facultyId);
                    request.setAttribute(RequestParameter.SPECIALTIES, specialties);
                    router = new Router(PagePath.ADMIN_SPECIALTIES);
                } else {
                    router = new Router(PagePath.ERROR_500);
                    logger.log(Level.ERROR, "Impossible add specialty to db");
                }
            } catch (ServiceException e) {
                router = new Router(PagePath.ERROR_500);
                logger.log(Level.ERROR, "Application error: ", e);
            }
        }
        return router;
    }
}
