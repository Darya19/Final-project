package com.epam.enrollee.model.entity;

import com.epam.enrollee.model.type.StatusType;

public class Faculty {

    private int facultyId;
    private String facultyName;
    private String facultyDescription;
    private StatusType facultyStatus;

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getFacultyDescription() {
        return facultyDescription;
    }

    public void setFacultyDescription(String facultyDescription) {
        this.facultyDescription = facultyDescription;
    }

    public StatusType getFacultyStatus() {
        return facultyStatus;
    }

    public void setFacultyStatus(StatusType facultyStatus) {
        this.facultyStatus = facultyStatus;
    }
}
