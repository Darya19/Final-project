package com.epam.enrollee.controller.command;

import com.epam.enrollee.controller.command.impl.EmptyCommand;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Action factory.
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
        if (action != null || !action.isEmpty()) {
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
