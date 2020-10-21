package com.epam.enrollee.model.enumtype;

public enum  ApplicationStatus {

    CONSIDERED("considered"),
    ACCEPTED("accepted"),
    REJECTED("rejected");

    private String applicationStatus;

    ApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }
}
