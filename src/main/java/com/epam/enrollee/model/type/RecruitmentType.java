package com.epam.enrollee.model.type;

public enum RecruitmentType {

    OPEN("open"),
    CLOSE("close");

    String type;

    RecruitmentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
