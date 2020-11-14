package com.epam.enrollee.model.type;

public enum StatusType {

    ACTIVE("active"),
    DELETED("deleted");

    private String status;

    StatusType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
