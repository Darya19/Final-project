package com.epam.enrollee.model.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Enrollee mark register.
 *
 * @author Darya Shcherbina
 * @version 1.0
 */
public class EnrolleeMarkRegister implements Comparable<EnrolleeMarkRegister> {

    private Map<Subject, Integer> testsSubjectsAndMarks;
    private int averageMark;

    /**
     * Instantiates a new Enrollee mark register.
     */
    public EnrolleeMarkRegister() {
        testsSubjectsAndMarks = new HashMap<>();
    }

    /**
     * Gets tests subjects and marks.
     *
     * @return the tests subjects and marks
     */
    public Map<Subject, Integer> getTestsSubjectsAndMarks() {
        return testsSubjectsAndMarks;
    }

    /**
     * Sets tests subjects and marks.
     *
     * @param testsSubjectsAndMarks the tests subjects and marks
     */
    public void setTestsSubjectsAndMarks(Map<Subject, Integer> testsSubjectsAndMarks) {
        this.testsSubjectsAndMarks = testsSubjectsAndMarks;
    }

    /**
     * Gets average mark.
     *
     * @return the average mark
     */
    public int getAverageMark() {
        return averageMark;
    }

    /**
     * Sets average mark.
     *
     * @param averageMark the average mark
     */
    public void setAverageMark(int averageMark) {
        this.averageMark = averageMark;
    }

    /**
     * Get integer.
     *
     * @param key the key
     * @return the integer
     */
    public Integer get(Subject key) {
        return testsSubjectsAndMarks.get(key);
    }

    /**
     * Put integer.
     *
     * @param key   the key
     * @param value the value
     * @return the integer
     */
    public Integer put(Subject key, Integer value) {
        return testsSubjectsAndMarks.put(key, value);
    }

    /**
     * Remove integer.
     *
     * @param key the key
     * @return the integer
     */
    public Integer remove(Subject key) {
        return testsSubjectsAndMarks.remove(key);
    }

    /**
     * Size int.
     *
     * @return the int
     */
    public int size() {
        return testsSubjectsAndMarks.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnrolleeMarkRegister register = (EnrolleeMarkRegister) o;

        if (getAverageMark() != register.getAverageMark()) return false;
        return getTestsSubjectsAndMarks() != null ? getTestsSubjectsAndMarks().equals(register.getTestsSubjectsAndMarks())
                : register.getTestsSubjectsAndMarks() == null;
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
        return (int) (this.averageMark - register.getAverageMark());
    }
}