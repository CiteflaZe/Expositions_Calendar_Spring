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

    public static Status valueOfByName(String statusName){
        if(statusName == null ||
                Arrays.stream(Status.values()).noneMatch(x -> x.name().equals(statusName.toUpperCase()))){
            return Status.FAILED;
        }else {
            return Status.valueOf(statusName.toUpperCase());
        }
    }
}
