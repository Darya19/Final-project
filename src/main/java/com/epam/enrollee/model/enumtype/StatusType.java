package com.epam.enrollee.model.enumtype;

public enum StatusType {

    ACTIVE("active"),
    INACTIVE("inactive"),
    DELETED("deleted");

    private String status;

    StatusType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
