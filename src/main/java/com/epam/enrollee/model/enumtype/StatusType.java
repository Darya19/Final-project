package com.epam.enrollee.model.enumtype;

public enum StatusType {
    BLOCKED("blocked"),
    ACTIVE("active"),
    DELETED("deleted");//TODO write status

    private String status;

    StatusType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
