package com.epam.enrollee.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Specialty {

    private int specialtyId;
    private String specialtyName;
    private List<Integer> subjectsId;

    public Specialty() {
        subjectsId = new ArrayList<>();
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

    public List<Integer> getSubjectsId() {
        return subjectsId;
    }

    public void setSubjectsId(List<Integer> subjectsId) {
        this.subjectsId = subjectsId;
    }

    public boolean add(Integer integer) {
        return subjectsId.add(integer);
    }

    public Integer get(int index) {
        return subjectsId.get(index);
    }

    public Integer remove(int index) {
        return subjectsId.remove(index);
    }
}
