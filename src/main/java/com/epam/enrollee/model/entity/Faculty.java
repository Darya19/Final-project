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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faculty faculty = (Faculty) o;

        if (getFacultyId() != faculty.getFacultyId()) return false;
        if (getFacultyName() != null ? !getFacultyName().equals(faculty.getFacultyName()) : faculty.getFacultyName() != null)
            return false;
        if (getFacultyDescription() != null ? !getFacultyDescription().equals(faculty.getFacultyDescription()) : faculty.getFacultyDescription() != null)
            return false;
        return getFacultyStatus() == faculty.getFacultyStatus();
    }

    @Override
    public int hashCode() {
        int result = getFacultyId();
        result = 31 * result + (getFacultyName() != null ? getFacultyName().hashCode() : 0);
        result = 31 * result + (getFacultyDescription() != null ? getFacultyDescription().hashCode() : 0);
        result = 31 * result + (getFacultyStatus() != null ? getFacultyStatus().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Faculty{");
        sb.append("facultyId=").append(facultyId);
        sb.append(", facultyName='").append(facultyName).append('\'');
        sb.append(", facultyDescription='").append(facultyDescription).append('\'');
        sb.append(", facultyStatus=").append(facultyStatus);
        sb.append('}');
        return sb.toString();
    }
}
