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
    EDIT_PERSONAL_INFORMATION {
        {
            this.command = new EditPersonalInformationCommand();
        }
    },
    EDIT_ENROLLEE_SPECIALTY {
        {
            this.command = new EditEnrolleeSpecialtyCommand();
        }
    },
    EDIT_MARKS {
        {
            this.command = new EditMarksCommand();
        }
    },
    TO_SPECIALTIES_PAGE {
        {
            this.command = new ToSpecialtiesPageCommand();
        }
    },
    DELETE_FACULTY {
        {
            this.command = new DeleteFacultyCommand();
        }
    };

    Command command;
    public Command getCommand() {
        return command;
    }
}
