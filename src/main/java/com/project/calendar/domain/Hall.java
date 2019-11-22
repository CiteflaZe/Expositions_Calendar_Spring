package com.project.calendar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Converts;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hall {

    private Long id;

    @NotEmpty(message = "Please provide hall name")
    private String name;

    @NotEmpty(message = "Please provide city")
    private String city;

    @NotEmpty(message = "Please provide street")
    private String street;

    @NotEmpty(message = "Please provide houseNumber")
    private Integer houseNumber;
}
