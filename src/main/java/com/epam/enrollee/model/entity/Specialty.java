package com.epam.enrollee.model.entity;

import com.epam.enrollee.model.type.RecruitmentType;
import com.epam.enrollee.model.type.StatusType;

/**
 * The type Specialty.
 */
public class Specialty {

    private int specialtyId;
    private String specialtyName;
    private RecruitmentType recruitment;
    private int numberOfSeats;
    private StatusType specialtyStatus;

    /**
     * Instantiates a new Specialty.
     */
    public Specialty() {
    }

    /**
     * Gets specialty id.
     *
     * @return the specialty id
     */
    public int getSpecialtyId() {
        return specialtyId;
    }

    /**
     * Sets specialty id.
     *
     * @param specialtyId the specialty id
     */
    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    /**
     * Gets specialty name.
     *
     * @return the specialty name
     */
    public String getSpecialtyName() {
        return specialtyName;
    }

    /**
     * Sets specialty name.
     *
     * @param specialtyName the specialty name
     */
    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    /**
     * Gets recruitment.
     *
     * @return the recruitment
     */
    public RecruitmentType getRecruitment() {
        return recruitment;
    }

    /**
     * Sets recruitment.
     *
     * @param recruitment the recruitment
     */
    public void setRecruitment(RecruitmentType recruitment) {
        this.recruitment = recruitment;
    }

    /**
     * Gets number of seats.
     *
     * @return the number of seats
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Sets number of seats.
     *
     * @param numberOfSeats the number of seats
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Gets specialty status.
     *
     * @return the specialty status
     */
    public StatusType getSpecialtyStatus() {
        return specialtyStatus;
    }

    /**
     * Sets specialty status.
     *
     * @param specialtyStatus the specialty status
     */
    public void setSpecialtyStatus(StatusType specialtyStatus) {
        this.specialtyStatus = specialtyStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Specialty specialty = (Specialty) o;

        if (getSpecialtyId() != specialty.getSpecialtyId()) return false;
        if (getNumberOfSeats() != specialty.getNumberOfSeats()) return false;
        if (getSpecialtyName() != null ? !getSpecialtyName().equals(specialty.getSpecialtyName()) : specialty.getSpecialtyName() != null)
            return false;
        if (getRecruitment() != specialty.getRecruitment()) return false;
        return getSpecialtyStatus() == specialty.getSpecialtyStatus();
    }

    @Override
    public int hashCode() {
        int result = getSpecialtyId();
        result = 31 * result + (getSpecialtyName() != null ? getSpecialtyName().hashCode() : 0);
        result = 31 * result + (getRecruitment() != null ? getRecruitment().hashCode() : 0);
        result = 31 * result + getNumberOfSeats();
        result = 31 * result + (getSpecialtyStatus() != null ? getSpecialtyStatus().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Specialty{");
        sb.append("specialtyId=").append(specialtyId);
        sb.append(", specialtyName='").append(specialtyName).append('\'');
        sb.append(", recruitment=").append(recruitment);
        sb.append(", numberOfSeats=").append(numberOfSeats);
        sb.append(", specialtyStatus=").append(specialtyStatus);
        sb.append('}');
        return sb.toString();
    }
}
