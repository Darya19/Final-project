package com.epam.enrollee.model.entity;

import java.util.HashMap;
import java.util.Map;

public class EnrolleeMarkRegister {

    private Map<Subject, Integer> testsSubjectsAndMarks;

    public EnrolleeMarkRegister() {
        testsSubjectsAndMarks = new HashMap<>();
    }

    public Map<Subject, Integer> getTestsSubjectsAndMarks() {
        return testsSubjectsAndMarks;
    }

    public void setTestsSubjectsAndMarks(Map<Subject, Integer> testsSubjectsAndMarks) {
        this.testsSubjectsAndMarks = testsSubjectsAndMarks;
    }

    public Integer get(Object key) {
        return testsSubjectsAndMarks.get(key);
    }

    public Integer put(Subject key, Integer value) {
        return testsSubjectsAndMarks.put(key, value);
    }

    public Integer remove(Object key) {
        return testsSubjectsAndMarks.remove(key);
    }

    public int size() {
        return testsSubjectsAndMarks.size();
    }
}
