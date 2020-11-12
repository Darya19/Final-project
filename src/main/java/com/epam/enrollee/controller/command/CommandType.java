package com.epam.enrollee.controller.command;

import com.epam.enrollee.controller.command.impl.*;
import com.epam.enrollee.controller.command.impl.page.*;

public enum CommandType {
    ADD_FACULTY {
        {
            this.command = new AddFacultyCommand();
        }
    },
    ADD_SPECIALTY {
        {
            this.command = new AddSpecialtyCommand();
        }
    },
    CHANGE_APPLICATION_STATUS {
        {
            this.command = new ChangeApplicationStatusCommand();
        }
    },
    CHANGE_LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    },
    CHANGE_RECRUITMENT {
        {
            this.command = new ChangeRecruitmentCommand();
        }
    },
    DELETE_FACULTY {
        {
            this.command = new DeleteFacultyCommand();
        }
    },
    DELETE_PROFILE {
        {
            this.command = new DeleteProfileCommand();
        }
    },
    DELETE_SPECIALTY {
        {
            this.command = new DeleteSpecialtyCommand();
        }
    },
    EDIT_ENROLLEE_SPECIALTY {
        {
            this.command = new EditEnrolleeSpecialtyCommand();
        }
    },
    EDIT_FACULTY {
        {
            this.command = new EditFacultyCommand();
        }
    },
    EDIT_MARKS {
        {
            this.command = new EditMarksCommand();
        }
    },
    EDIT_PERSONAL_INFORMATION {
        {
            this.command = new EditPersonalInformationCommand();
        }
    },
    EDIT_SPECIALTY {
        {
            this.command = new EditSpecialtyCommand();
        }
    },
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
    TO_ADMIN_FACULTIES_PAGE {
        {
            this.command = new ToAdminFacultiesPageCommand();
        }
    },
    TO_ADMIN_SPECIALTIES_PAGE {
        {
            this.command = new ToAdminSpecialtiesPageCommand();
        }
    },
    TO_APPLICATIONS_PAGE {
        {
            this.command = new ToApplicationsPageCommand();
        }
    },
    TO_EDIT_FACULTY_PAGE {
        {
            this.command = new ToEditFacultyPageCommand();
        }
    },
    TO_EDIT_PROFILE_PAGE {
        {
            this.command = new ToEditProfilePageCommand();
        }
    },
    TO_EDIT_SPECIALTY_PAGE {
        {
            this.command = new ToEditSpecialtyPageCommand();
        }
    },
    TO_FACULTIES_PAGE {
        {
            this.command = new ToFacultiesPageCommand();
        }
    },
    TO_LOGIN_PAGE {
        {
            this.command = new ToLoginPageCommand();
        }
    },
    TO_MAIN_PAGE {
        {
            this.command = new ToMainPageCommand();
        }
    },
    TO_PROFILE_PAGE {
        {
            this.command = new ToProfilePageCommand();
        }
    },
    TO_REGISTER_PAGE {
        {
            this.command = new ToRegisterPageCommand();
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
