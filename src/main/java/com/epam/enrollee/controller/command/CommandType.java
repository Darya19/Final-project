package com.epam.enrollee.controller.command;

import com.epam.enrollee.controller.command.impl.*;
import com.epam.enrollee.controller.command.impl.page.*;

/**
 * The enum Command type.
 */
public enum CommandType {
    /**
     * The Add faculty.
     */
    ADD_FACULTY {
        {
            this.command = new AddFacultyCommand();
        }
    },
    /**
     * The Add specialty.
     */
    ADD_SPECIALTY {
        {
            this.command = new AddSpecialtyCommand();
        }
    },
    /**
     * The Change application status.
     */
    CHANGE_APPLICATION_STATUS {
        {
            this.command = new ChangeApplicationStatusCommand();
        }
    },
    /**
     * The Change language.
     */
    CHANGE_LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    },
    /**
     * The Change recruitment.
     */
    CHANGE_RECRUITMENT {
        {
            this.command = new ChangeRecruitmentCommand();
        }
    },
    /**
     * The Delete faculty.
     */
    DELETE_FACULTY {
        {
            this.command = new DeleteFacultyCommand();
        }
    },
    /**
     * The Delete profile.
     */
    DELETE_PROFILE {
        {
            this.command = new DeleteProfileCommand();
        }
    },
    /**
     * The Delete specialty.
     */
    DELETE_SPECIALTY {
        {
            this.command = new DeleteSpecialtyCommand();
        }
    },
    /**
     * The Edit enrollee specialty.
     */
    EDIT_ENROLLEE_SPECIALTY {
        {
            this.command = new EditEnrolleeSpecialtyCommand();
        }
    },
    /**
     * The Edit faculty.
     */
    EDIT_FACULTY {
        {
            this.command = new EditFacultyCommand();
        }
    },
    /**
     * The Edit marks.
     */
    EDIT_MARKS {
        {
            this.command = new EditMarksCommand();
        }
    },
    /**
     * The Edit personal information.
     */
    EDIT_PERSONAL_INFORMATION {
        {
            this.command = new EditPersonalInformationCommand();
        }
    },
    /**
     * The Edit specialty.
     */
    EDIT_SPECIALTY {
        {
            this.command = new EditSpecialtyCommand();
        }
    },
    /**
     * The Login.
     */
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    /**
     * The Logout.
     */
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    /**
     * The New application.
     */
    NEW_APPLICATION {
        {
            this.command = new NewApplicationCommand();
        }
    },
    /**
     * The Pagination.
     */
    PAGINATION {
        {
            this.command = new PaginationCommand();
        }
    },
    /**
     * The Register.
     */
    REGISTER {
        {
            this.command = new RegisterCommand();
        }
    },
    /**
     * The To admin faculties page.
     */
    TO_ADMIN_FACULTIES_PAGE {
        {
            this.command = new ToAdminFacultiesPageCommand();
        }
    },
    /**
     * The To admin specialties page.
     */
    TO_ADMIN_SPECIALTIES_PAGE {
        {
            this.command = new ToAdminSpecialtiesPageCommand();
        }
    },
    /**
     * The To applications page.
     */
    TO_APPLICATIONS_PAGE {
        {
            this.command = new ToApplicationsPageCommand();
        }
    },
    /**
     * The To edit application.
     */
    TO_EDIT_APPLICATION {
        {
            this.command = new ToEditApplicationCommand();
        }
    },
    /**
     * The To edit faculty page.
     */
    TO_EDIT_FACULTY_PAGE {
        {
            this.command = new ToEditFacultyPageCommand();
        }
    },
    /**
     * The To edit profile page.
     */
    TO_EDIT_PROFILE_PAGE {
        {
            this.command = new ToEditProfilePageCommand();
        }
    },
    /**
     * The To edit specialty page.
     */
    TO_EDIT_SPECIALTY_PAGE {
        {
            this.command = new ToEditSpecialtyPageCommand();
        }
    },
    /**
     * The To faculties page.
     */
    TO_FACULTIES_PAGE {
        {
            this.command = new ToFacultiesPageCommand();
        }
    },
    /**
     * The To login page.
     */
    TO_LOGIN_PAGE {
        {
            this.command = new ToLoginPageCommand();
        }
    },
    /**
     * The To main page.
     */
    TO_MAIN_PAGE {
        {
            this.command = new ToMainPageCommand();
        }
    },
    /**
     * The To profile page.
     */
    TO_PROFILE_PAGE {
        {
            this.command = new ToProfilePageCommand();
        }
    },
    /**
     * The To register page.
     */
    TO_REGISTER_PAGE {
        {
            this.command = new ToRegisterPageCommand();
        }
    },
    /**
     * The To specialties page.
     */
    TO_SPECIALTIES_PAGE {
        {
            this.command = new ToSpecialtiesPageCommand();
        }
    };

    /**
     * The Command.
     */
    Command command;

    /**
     * Gets command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }
}
