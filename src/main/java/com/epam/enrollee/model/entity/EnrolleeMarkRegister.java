package com.epam.enrollee.model.entity;

import java.util.HashMap;
import java.util.Map;

public class EnrolleeMarkRegister implements Comparable<EnrolleeMarkRegister>{

    private Map<Subject, Integer> testsSubjectsAndMarks;
    private int averageMark;

    public EnrolleeMarkRegister() {
        testsSubjectsAndMarks = new HashMap<>();
    }

    public Map<Subject, Integer> getTestsSubjectsAndMarks() {
        return testsSubjectsAndMarks;
    }

    public void setTestsSubjectsAndMarks(Map<Subject, Integer> testsSubjectsAndMarks) {
        this.testsSubjectsAndMarks = testsSubjectsAndMarks;
    }

    public int getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(int averageMark) {
        this.averageMark = averageMark;
    }

    public Integer get(Subject key) {
        return testsSubjectsAndMarks.get(key);
    }

    public Integer put(Subject key, Integer value) {
        return testsSubjectsAndMarks.put(key, value);
    }

    public Integer remove(Subject key) {
        return testsSubjectsAndMarks.remove(key);
    }

    public int size() {
        return testsSubjectsAndMarks.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnrolleeMarkRegister register = (EnrolleeMarkRegister) o;

        if (getAverageMark() != register.getAverageMark()) return false;
        return getTestsSubjectsAndMarks() != null ? getTestsSubjectsAndMarks().equals(register.getTestsSubjectsAndMarks()) : register.getTestsSubjectsAndMarks() == null;
    }

    @Override
    public int hashCode() {
        int result = getTestsSubjectsAndMarks() != null ? getTestsSubjectsAndMarks().hashCode() : 0;
        result = 31 * result + getAverageMark();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EnrolleeMarkRegister{");
        sb.append("testsSubjectsAndMarks=").append(testsSubjectsAndMarks);
        sb.append(", averageMark=").append(averageMark);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(EnrolleeMarkRegister register) {
        return (int)(this.averageMark - register.getAverageMark());
    }
}
