package com.epam.enrollee.model.entity;

import com.epam.enrollee.model.type.ApplicationStatus;

/**
 * The type Enrollee.
 */
public class Enrollee extends User {

    private String firstName;
    private String lastName;
    private String middleName;
    private int chosenFacultyId;
    private int chosenSpecialtyId;
    private ApplicationStatus applicationStatus;

    /**
     * Instantiates a new Enrollee.
     */
    public Enrollee() {
        super();
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets middle name.
     *
     * @return the middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets middle name.
     *
     * @param middleName the middle name
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Gets chosen faculty id.
     *
     * @return the chosen faculty id
     */
    public int getChosenFacultyId() {
        return chosenFacultyId;
    }

    /**
     * Sets chosen faculty id.
     *
     * @param chosenFacultyId the chosen faculty id
     */
    public void setChosenFacultyId(int chosenFacultyId) {
        this.chosenFacultyId = chosenFacultyId;
    }

    /**
     * Gets chosen specialty id.
     *
     * @return the chosen specialty id
     */
    public int getChosenSpecialtyId() {
        return chosenSpecialtyId;
    }

    /**
     * Sets chosen specialty id.
     *
     * @param chosenSpecialtyId the chosen specialty id
     */
    public void setChosenSpecialtyId(int chosenSpecialtyId) {
        this.chosenSpecialtyId = chosenSpecialtyId;
    }

    /**
     * Gets application status.
     *
     * @return the application status
     */
    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    /**
     * Sets application status.
     *
     * @param applicationStatus the application status
     */
    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Enrollee enrollee = (Enrollee) o;

        if (getChosenFacultyId() != enrollee.getChosenFacultyId()) return false;
        if (getChosenSpecialtyId() != enrollee.getChosenSpecialtyId()) return false;
        if (getFirstName() != null ? !getFirstName().equals(enrollee.getFirstName()) : enrollee.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(enrollee.getLastName()) : enrollee.getLastName() != null)
            return false;
        if (getMiddleName() != null ? !getMiddleName().equals(enrollee.getMiddleName()) : enrollee.getMiddleName() != null)
            return false;
        return getApplicationStatus() == enrollee.getApplicationStatus();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getMiddleName() != null ? getMiddleName().hashCode() : 0);
        result = 31 * result + getChosenFacultyId();
        result = 31 * result + getChosenSpecialtyId();
        result = 31 * result + (getApplicationStatus() != null ? getApplicationStatus().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Enrollee{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", middleName='").append(middleName).append('\'');
        sb.append(", chosenFacultyId=").append(chosenFacultyId);
        sb.append(", chosenSpecialtyId=").append(chosenSpecialtyId);
        sb.append(", applicationStatus=").append(applicationStatus);
        sb.append('}');
        return sb.toString();
    }
}


