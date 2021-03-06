package com.epam.enrollee.controller.command.impl.page;

import com.epam.enrollee.controller.command.Command;
import com.epam.enrollee.controller.command.PagePath;
import com.epam.enrollee.controller.router.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The type To profile page command.
 * Command to go to the profile page. Command used by user with role user.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class ToProfilePageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.PROFILE);
    }
}