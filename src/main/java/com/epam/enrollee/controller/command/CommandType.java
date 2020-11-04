package com.epam.enrollee.controller.command;

import com.epam.enrollee.controller.command.impl.*;

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
    },
    TO_FACULTIES_PAGE {
        {
            this.command = new ToFacultiesPageCommand();
        }
    },
    TO_MAIN_PAGE {
        {
            this.command = new ToMainPageCommand();
        }
    },
    TO_REGISTER_PAGE {
        {
            this.command = new ToRegisterPageCommand();
        }
    },
    TO_LOGOUT_PAGE {
        {
            this.command = new ToLogoutPageCommand();
        }
    },
    TO_LOGIN_PAGE {
        {
            this.command = new ToLoginPageCommand();
        }
    },
    TO_EDIT_PROFILE_PAGE {
        {
            this.command = new ToEditProfilePageCommand();
        }
    },
    EDIT {
        {
            this.command = new EditCommand();
        }
    },
    TO_SPECIALTIES_PAGE {
        {
            this.command = new ToSpecialtiesPageCommand();
        }
    };

    Command command;
    public Command getCommand() {
        return command;
    }
}
