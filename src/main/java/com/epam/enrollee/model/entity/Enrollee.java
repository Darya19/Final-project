package com.epam.enrollee.model.entity;

import com.epam.enrollee.model.type.ApplicationStatus;

public class Enrollee extends User {

    private String firstName;
    private String lastName;
    private String middleName;
    private int chosenFacultyId;
    private int chosenSpecialtyId;
    private ApplicationStatus applicationStatus;

    public Enrollee() {
        super();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getChosenFacultyId() {
        return chosenFacultyId;
    }

    public void setChosenFacultyId(int chosenFacultyId) {
        this.chosenFacultyId = chosenFacultyId;
    }

    public int getChosenSpecialtyId() {
        return chosenSpecialtyId;
    }

    public void setChosenSpecialtyId(int chosenSpecialtyId) {
        this.chosenSpecialtyId = chosenSpecialtyId;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

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


