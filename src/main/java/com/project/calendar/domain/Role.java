package com.project.calendar.domain;

public enum Role {
    ADMIN("Admin"), USER("User");

    String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
