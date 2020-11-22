package com.epam.enrollee.controller.command.impl;

import com.epam.enrollee.controller.command.AttributeName;
import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.router.Router;
import com.epam.enrollee.exception.ServiceException;
import com.epam.enrollee.model.entity.Enrollee;
import com.epam.enrollee.model.entity.Passport;
import com.epam.enrollee.model.service.EnrolleeService;
import com.epam.enrollee.model.service.impl.EnrolleeServiceImpl;
import com.epam.enrollee.util.MapKeys;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Delete profile command.
 * The command can be used by user with role user. If the profile is deleted successfully,
 * the enrollee with all information about him deleting from database.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class DeleteProfileCommand implements Command {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        EnrolleeService enrolleeService = EnrolleeServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Router router = null;
        Map<String, Object> parameters = new HashMap<>();
        Enrollee enrollee = (Enrollee) session.getAttribute(AttributeName.ENROLLEE);
        Passport passport = (Passport) session.getAttribute(AttributeName.PASSPORT);
        parameters.put(MapKeys.ENROLLEE, enrollee);
        parameters.put(MapKeys.PASSPORT, passport);
        try {
            if (enrolleeService.remove(parameters)) {
                router = new Router(PagePath.INDEX);
                session.invalidate();
            }
        } catch (ServiceException e) {
            router = new Router(PagePath.ERROR_500);
            logger.log(Level.ERROR, "Application error: ", e);
        }
        return router;
    }
}