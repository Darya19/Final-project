package com.epam.enrollee.model.type;

/**
 * The enum Recruitment type.
 * Defines a recruitment for a specialty. In the case of an open recruitment,
 * users with the user role can send applications for this specialty,
 * in the case of a closed recruitment, an application cannot be sent.
 * The recruitment status is determined by the user as the admin.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public enum RecruitmentType {

    /**
     * Opened recruitment type.
     */
    OPENED("opened"),
    /**
     * Closed recruitment type.
     */
    CLOSED("closed");

    /**
     * The Type.
     */
    String type;

    RecruitmentType(String type) {
        this.type = type;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }
}
