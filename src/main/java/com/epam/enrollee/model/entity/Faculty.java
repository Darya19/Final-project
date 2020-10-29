package com.epam.enrollee.model.entity;

import java.util.ArrayList;
import java.util.List;

public class Faculty {

    private int facultyId;
    private String facultyName;
    private List<Integer> specialtiesId;

    public Faculty() {
        specialtiesId = new ArrayList<>();
    }

    public List<Integer> getSpecialtiesId() {
        return specialtiesId;
    }

    public void setSpecialtiesId(List<Integer> specialtiesId) {
        this.specialtiesId = specialtiesId;
    }

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

    public boolean add(Integer integer) {
        return getSpecialtiesId().add(integer);
    }

    public Integer get(int index) {
        return getSpecialtiesId().get(index);
    }

    public Integer remove(int index) {
        return getSpecialtiesId().remove(index);
    }
}
