package com.project.calendar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exposition {

    private Long id;

    @NotEmpty(message = "Please provide exposition title")
    private String title;

    @NotEmpty(message = "Please provide exposition theme")
    private String theme;

    @NotNull(message = "Provide valid start date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "Provide valid ending date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = "Please provide ticket price")
    private BigDecimal ticketPrice;

    @NotEmpty(message = "Please provide exposition description")
    private String description;

    private Hall hall;

}
