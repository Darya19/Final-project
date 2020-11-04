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
}


