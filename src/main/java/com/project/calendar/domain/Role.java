package com.project.calendar.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;

public enum Role implements GrantedAuthority {
    ADMIN("Admin"), USER("User");

    private String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {

        return description;
    }

    public static Role valueOfByName(String roleName) {
        return Arrays.stream(Role.values())
                .filter(x -> x.name().equalsIgnoreCase(roleName))
                .findAny()
                .orElse(Role.USER);
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
