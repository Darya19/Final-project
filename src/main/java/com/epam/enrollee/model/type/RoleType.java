package com.epam.enrollee.model.type;

public enum RoleType {
    ADMIN("admin"),
    USER("user"),
    GUEST("guest");

   private String role;

    RoleType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
