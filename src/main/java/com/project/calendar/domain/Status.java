package com.project.calendar.domain;

import java.util.Arrays;

public enum Status {
    PASSED("Passed"), FAILED("Failed");

    String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Status valueOfByName(String statusName) {
        return Arrays.stream(Status.values())
                .filter(x -> x.name().equalsIgnoreCase(statusName))
                .findAny()
                .orElse(Status.FAILED);
    }
}
