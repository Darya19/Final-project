package com.epam.enrollee.model.type;

/**
 * The enum Status type.
 * Defines the status for the faculty and specialty. In the case of the status deleted,
 * the information is stored in the database, but users do not have access to it. When the status
 * is active, a user with the admin role can do various actions with the selected object,
 * while using the user role, the user can select an object to send an application.
 * The recruitment status is determined by the user as the admin.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public enum StatusType {

    /**
     * Active status type.
     */
    ACTIVE("active"),
    /**
     * Deleted status type.
     */
    DELETED("deleted");

    private String status;

    StatusType(String status) {
        this.status = status;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }
}
