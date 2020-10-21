package com.epam.enrollee.controller.command;

import com.epam.enrollee.controller.command.impl.LoginCommand;
import com.epam.enrollee.controller.command.impl.LogoutCommand;
import com.epam.enrollee.controller.command.impl.ProfileCommand;
import com.epam.enrollee.controller.command.impl.RegisterCommand;

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
    },
    REGISTER {
        {
            this.command = new RegisterCommand();
        }
    },
    PROFILE {
        {
            this.command = new ProfileCommand();
        }
    };

    Command command;
    public Command getCommand() {
        return command;
    }
}
