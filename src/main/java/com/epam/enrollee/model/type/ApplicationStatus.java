package com.epam.enrollee.model.type;

/**
 * The enum Application status.
 * Determines the status of the application. When creating an application, it has the status
 * of being considered, the administrator can accept or reject the application, and as a
 * result the status changes accordingly. When the status of recruitment for a specialty is
 * changed to closed, all applications receive an archive status.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public enum ApplicationStatus {

    /**
     * Considered application status.
     */
    CONSIDERED("considered"),
    /**
     * Accepted application status.
     */
    ACCEPTED("accepted"),
    /**
     * Rejected application status.
     */
    REJECTED("rejected"),
    /**
     * Archived application status.
     */
    ARCHIVED("archived");

    private String applicationStatus;

    ApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    /**
     * Gets application status.
     *
     * @return the application status
     */
    public String getApplicationStatus() {
        return applicationStatus;
    }
}
