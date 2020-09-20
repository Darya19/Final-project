package com.epam.task1.command;

import com.epam.task1.command.impl.LoginCommand;
import com.epam.task1.command.impl.LogoutCommand;

public enum CommandType {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    };

    Command command;
    public Command getCommand() {
        return command;
    }
}
