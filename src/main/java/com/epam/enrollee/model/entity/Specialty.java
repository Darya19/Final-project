package com.epam.enrollee.model.entity;

import com.epam.enrollee.model.type.RecruitmentType;
import com.epam.enrollee.model.type.StatusType;

public class Specialty {

    private int specialtyId;
    private String specialtyName;
    private RecruitmentType recruitment;
    private int numberOfSeats;
    private StatusType specialtyStatus;

    public Specialty() {
    }

    public int getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(int specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public RecruitmentType getRecruitment() {
        return recruitment;
    }

    public void setRecruitment(RecruitmentType recruitment) {
        this.recruitment = recruitment;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public StatusType getSpecialtyStatus() {
        return specialtyStatus;
    }

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
