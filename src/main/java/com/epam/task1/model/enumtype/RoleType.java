package com.epam.task1.model.enumtype;

public enum RoleType {
    ADMIN("admin"),
    USER("user");

   private String role;

    RoleType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
