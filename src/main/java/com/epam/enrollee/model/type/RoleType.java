package com.epam.enrollee.model.type;

/**
 * The enum Role type.
 * Defines the user's role for further work in the system.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public enum RoleType {
    /**
     * Admin role type.
     */
    ADMIN("admin"),
    /**
     * User role type.
     */
    USER("user"),
    /**
     * Guest role type.
     */
    GUEST("guest");

    private String role;

    RoleType(String role) {
        this.role = role;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }
}
