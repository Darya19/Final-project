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
}
