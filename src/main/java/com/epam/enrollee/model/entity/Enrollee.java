package com.epam.enrollee.model.entity;

import com.epam.enrollee.model.enumtype.ApplicationStatus;

import java.util.HashMap;
import java.util.Map;

public class Enrollee extends User {

    private String firstName;
    private String lastName;
    private String middleName;
    private int chosenFacultyId;
    private int chosenSpecialtyId;
    private Map<Integer, Integer> testsSubjectsAndMarks;

    private ApplicationStatus applicationStatus;

    public Enrollee() {
        super();
      testsSubjectsAndMarks = new HashMap<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public int getChosenFacultyId() {
        return chosenFacultyId;
    }

    public void setChosenFacultyId(int chosenFacultyId) {
        this.chosenFacultyId = chosenFacultyId;
    }

    public int getChosenSpecialtyId() {
        return chosenSpecialtyId;
    }

    public void setChosenSpecialtyId(int chosenSpecialtyId) {
        this.chosenSpecialtyId = chosenSpecialtyId;
    }

    public Map<Integer, Integer> getTestsSubjectsAndMarks() {
        return testsSubjectsAndMarks;
    }

    public void setTestsSubjectsAndMarks(Map<Integer, Integer> testsSubjectsAndMarks) {
        this.testsSubjectsAndMarks = testsSubjectsAndMarks;
    }

    public Integer get(Object key) {
        return testsSubjectsAndMarks.get(key);
    }

    public Integer put(Integer key, Integer value) {
        return testsSubjectsAndMarks.put(key, value);
    }

    public Integer remove(Object key) {
        return testsSubjectsAndMarks.remove(key);
    }

    public int size() {
        return testsSubjectsAndMarks.size();
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }
}


