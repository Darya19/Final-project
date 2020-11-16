package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.EnrolleeMarkRegister;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.entity.Specialty;
import com.epam.enrollee.model.service.EnrolleeMarkRegisterService;
import com.epam.enrollee.model.service.EnrolleeService;
import com.epam.enrollee.model.service.FacultyService;
import com.epam.enrollee.model.service.SpecialtyService;
import com.epam.enrollee.model.service.impl.EnrolleeMarkRegisterServiceImpl;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import com.epam.enrollee.model.service.impl.SpecialtyServiceImpl;
import com.epam.enrollee.model.type.ApplicationStatus;
import com.epam.enrollee.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class NewApplicationCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeService enrolleeService = EnrolleeServiceImpl.getInstance();
        EnrolleeMarkRegisterService registerService = EnrolleeMarkRegisterServiceImpl.getInstance();
        SpecialtyService specialtyService = SpecialtyServiceImpl.getInstance();
        FacultyService facultyService = FacultyServiceImpl.getInstance();
        Map<String, String> parameters = new HashMap<>();
        HttpSession session = request.getSession();
        boolean isUpdated;
        Router router;
        String facultyId = request.getParameter(RequestParameter.FACULTY_ID);
        String specialtyId = request.getParameter(RequestParameter.SPECIALTY_ID);
        parameters.put(MapKeys.SPECIALTY_ID, specialtyId);
        parameters.put(MapKeys.FACULTY_ID, facultyId);
        parameters.put(MapKeys.SUBJECT_ID_1, request.getParameter(RequestParameter.SUBJECT_ID_1));
        parameters.put(MapKeys.MARK_1, request.getParameter(RequestParameter.MARK_1));
        parameters.put(MapKeys.SUBJECT_ID_2, request.getParameter(RequestParameter.SUBJECT_ID_2));
        parameters.put(MapKeys.MARK_2, request.getParameter(RequestParameter.MARK_2));
        parameters.put(MapKeys.SUBJECT_ID_3, request.getParameter(RequestParameter.SUBJECT_ID_3));
        parameters.put(MapKeys.MARK_3, request.getParameter(RequestParameter.MARK_3));
        parameters.put(MapKeys.SUBJECT_ID_4, request.getParameter(RequestParameter.SUBJECT_ID_4));
        parameters.put(MapKeys.MARK_4, request.getParameter(RequestParameter.MARK_4));
        try {
            Enrollee enrollee = (Enrollee) session.getAttribute(AttributeName.ENROLLEE);
            EnrolleeMarkRegister registerInSession = (EnrolleeMarkRegister) session.getAttribute(AttributeName.REGISTER);
            parameters = enrolleeService.checkParameters(parameters);
            if (parameters.containsValue("")) {
                request.setAttribute(AttributeName.PARAMETERS, parameters);
                if (putFacultiesSpecialtiesSubjectsInRequest(request)) {
                    router = new Router(PagePath.EDIT_APPLICATION);
                } else {
                    router = new Router(PagePath.ERROR);
                    logger.log(Level.ERROR, "Impossible create enrollee or register");
                }
            } else {
                Optional<EnrolleeMarkRegister> register = registerService.updateEnrolleeRegister(enrollee.getUserId(),
                        registerInSession, parameters);
                if (enrolleeService.updateEnrolleeFaculty(enrollee, facultyId) && enrolleeService.updateEnrolleeSpecialty
                        (enrollee, specialtyId) && register.isPresent()) {
                    Optional<Specialty> specialty = specialtyService.findEnrolleeSpecialty(enrollee.getUserId());
                    Optional<Faculty> faculty = facultyService.findEnrolleeFaculty(enrollee.getUserId());
                    if (specialty.isPresent() && faculty.isPresent()) {
                        session.removeAttribute(AttributeName.SPECIALTY);
                        session.removeAttribute(AttributeName.FACULTY);
                        session.setAttribute(AttributeName.FACULTY, faculty.get());
                        session.setAttribute(AttributeName.SPECIALTY, specialty.get());
                        enrollee.setApplicationStatus(ApplicationStatus.CONSIDERED);
                        router = new Router(PagePath.PROFILE);
                    } else {
                        router = new Router(PagePath.ERROR);
                        logger.log(Level.ERROR, "Impossible create faculty or specialty for enrollee");
                    }
                } else {
                    router = new Router(PagePath.ERROR);
                    logger.log(Level.ERROR, "Incorrect enrollee parameters");
                }
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}

