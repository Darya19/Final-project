package com.epam.enrollee.model.type;

public enum  ApplicationStatus {

    CONSIDERED("considered"),
    ACCEPTED("accepted"),
    REJECTED("rejected"),
    ARCHIVED("archived");

    private String applicationStatus;

    ApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }
}
