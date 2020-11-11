package com.epam.enrollee.controller.command;

import com.epam.enrollee.controller.command.impl.EmptyCommand;

import javax.servlet.http.HttpServletRequest;

public class ActionFactory {

    private static final String WRONG_COMMAND = "command not found";

    public static Command defineCommand(HttpServletRequest request) {
        Command current = null;
        String action = request.getParameter("command");
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
