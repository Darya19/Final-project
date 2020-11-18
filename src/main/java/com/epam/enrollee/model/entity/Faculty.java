package com.epam.enrollee.model.entity;

import com.epam.enrollee.model.type.StatusType;

/**
 * The type Faculty.
 */
public class Faculty {

    private int facultyId;
    private String facultyName;
    private String facultyDescription;
    private StatusType facultyStatus;

    /**
     * Gets faculty id.
     *
     * @return the faculty id
     */
    public int getFacultyId() {
        return facultyId;
    }

    /**
     * Sets faculty id.
     *
     * @param facultyId the faculty id
     */
    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    /**
     * Gets faculty name.
     *
     * @return the faculty name
     */
    public String getFacultyName() {
        return facultyName;
    }

    /**
     * Sets faculty name.
     *
     * @param facultyName the faculty name
     */
    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    /**
     * Gets faculty description.
     *
     * @return the faculty description
     */
    public String getFacultyDescription() {
        return facultyDescription;
    }

    /**
     * Sets faculty description.
     *
     * @param facultyDescription the faculty description
     */
    public void setFacultyDescription(String facultyDescription) {
        this.facultyDescription = facultyDescription;
    }

    /**
     * Gets faculty status.
     *
     * @return the faculty status
     */
    public StatusType getFacultyStatus() {
        return facultyStatus;
    }

    /**
     * Sets faculty status.
     *
     * @param facultyStatus the faculty status
     */
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
