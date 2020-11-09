package com.epam.enrollee.model.type;

public enum RecruitmentType {

    OPENED("opened"),
    CLOSED("closed");

    String type;

    RecruitmentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
