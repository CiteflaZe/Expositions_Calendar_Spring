package com.project.calendar.domain;

import java.util.Arrays;

public enum Role {
    ADMIN("Admin"), USER("User");

    String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {

        return description;
    }
    public static Role valueOfByName(String roleName){
        if(roleName == null ||
                Arrays.stream(Role.values()).noneMatch(x -> x.name().equals(roleName.toUpperCase()))){
            return Role.USER;
        }else {
            return Role.valueOf(roleName.toUpperCase());
        }
    }
}
