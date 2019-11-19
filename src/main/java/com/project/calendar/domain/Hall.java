package com.project.calendar.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class Hall {

    private final Long id;

    @NotEmpty(message = "Please provide hall name")
    private final String name;

    @NotEmpty(message = "Please provide city")
    private final String city;

    @NotEmpty(message = "Please provide street")
    private final String street;

    @NotEmpty(message = "Please provide houseNumber")
    private final Integer houseNumber;
}
