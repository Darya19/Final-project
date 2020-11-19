package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.command.RequestParameter;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Faculty;
import com.epam.enrollee.model.entity.User;
import com.epam.enrollee.model.service.FacultyService;
import com.epam.enrollee.model.service.UserService;
import com.epam.enrollee.model.service.impl.FacultyServiceImpl;
import com.epam.enrollee.model.service.impl.UserServiceImpl;
import com.epam.enrollee.model.type.RoleType;
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


/**
 * The type Login command.
 * Command for determining the role of the user for further work in the system.
 * After verification, user data is saved to the session. In case of incorrectly entered data,
 * the user is returned to the login page.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class LoginCommand implements Command {

    private static Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        UserService userService = UserServiceImpl.getInstance();
        FacultyService facultyService = FacultyServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Map<String, String> parameters = new HashMap<>();
        Router router;
        String email = request.getParameter(RequestParameter.EMAIL);
        String password = request.getParameter(RequestParameter.PASSWORD);
        try {
            if (userService.checkEmailAndPassword(email, password)) {
                Optional<User> optionalUser = userService.findUserByEmail(email);
                if (optionalUser.isPresent()) {
                    if (optionalUser.get().getRole().equals(RoleType.USER)) {
                        int enrolleeId = putInSessionEnrolleeAndPassportAndMarks(email, session);
                        boolean isAdded = putEnrolleeFacultyAndSpecialtyInSession(enrolleeId, session);
                        if (enrolleeId > 0 && isAdded) {
                            session.removeAttribute(AttributeName.ROLE);
                            session.setAttribute(AttributeName.ROLE, RoleType.USER);
                            router = new Router(PagePath.PROFILE);
                        } else {
                            router = new Router(PagePath.ERROR);
                            logger.log(Level.ERROR, "Impossible find enrollee in db");
                        }
                    } else {
                        User user = optionalUser.get();
                        session.setAttribute(AttributeName.ENROLLEE, user);
                        List<Faculty> faculties = facultyService.findAllActiveFaculties();
                        request.setAttribute(AttributeName.FACULTIES, faculties);
                        session.removeAttribute(AttributeName.ROLE);
                        session.setAttribute(AttributeName.ROLE, RoleType.ADMIN);
                        router = new Router(PagePath.ADMIN_FACULTIES);
                    }
                } else {
                    router = new Router(PagePath.ERROR);
                    logger.log(Level.ERROR, "Impossible find enrollee faculty or specialty in db");
                }
            } else {
                parameters.put(MapKeys.EMAIL, email);
                parameters.put(MapKeys.PASSWORD, password);
                request.setAttribute(AttributeName.PARAMETERS, parameters);
                router = new Router(PagePath.LOGIN);
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error:", e);
        }
        return router;
    }
}