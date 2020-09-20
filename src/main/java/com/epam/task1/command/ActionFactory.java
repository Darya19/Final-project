package com.epam.task1.command;

import com.epam.task1.command.impl.EmptyCommand;

import javax.servlet.http.HttpServletRequest;

import static com.epam.task1.command.PagePath.PROJECT_ERROR;

public class ActionFactory {

    private static final String WRONG_COMMAND = "command not found";

    public static Command defineCommand(HttpServletRequest request) {
        Command current = new EmptyCommand();
        String action = request.getParameter("command");
        if (action == null || action.isEmpty()) {
            return current;
        }
        try {
            CommandType commandType = CommandType.valueOf(action.toUpperCase());
            current = commandType.getCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute("wrongAction", WRONG_COMMAND);
        }
        return current;
    }
}
