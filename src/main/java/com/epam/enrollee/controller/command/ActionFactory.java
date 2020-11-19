package com.epam.enrollee.controller.command;

import com.epam.enrollee.controller.command.impl.EmptyCommand;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Action factory.
 * Determines the type of command for its subsequent execution.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class ActionFactory {

    /**
     * Define command command.
     *
     * @param request the request
     * @return the command
     */
    public static Command defineCommand(HttpServletRequest request) {
        Command current;
        String action = request.getParameter(RequestParameter.COMMAND);
        if (action != null) {
            try {
                CommandType commandType = CommandType.valueOf(action.toUpperCase());
                current = commandType.getCommand();
            } catch (IllegalArgumentException e) {
                current = new EmptyCommand();
            }
        } else {
            current = new EmptyCommand();
        }
        return current;
    }
}
