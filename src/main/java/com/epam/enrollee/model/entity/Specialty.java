package com.epam.enrollee.model.entity;

import com.epam.enrollee.model.type.RecruitmentType;

import java.util.ArrayList;
import java.util.List;

public class Specialty {

    private int specialtyId;
    private String specialtyName;
    private RecruitmentType recruitment;
private int NumberOfSeats;

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
        return NumberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        NumberOfSeats = numberOfSeats;
    }
}
