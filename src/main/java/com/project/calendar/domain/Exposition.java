package com.project.calendar.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class Exposition {

    private final Integer id;

    @NotEmpty(message = "Please provide exposition title")
    private final String title;

    @NotEmpty(message = "Please provide exposition theme")
    private final String theme;

    @NotEmpty(message = "Please provide exposition start date")
    private final LocalDate startDate;

    @NotEmpty(message = "Please provide exposition end date")
    private final LocalDate endDate;

    @NotEmpty(message = "Please provide ticket price")
    private final BigDecimal ticketPrice;

    @NotEmpty(message = "Please provide exposition description")
    private final String description;
}
