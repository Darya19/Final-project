package com.epam.enrollee.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Specialty {

    private int specialtyId;
    private String specialtyName;

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

}
