package com.project.calendar.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
public class Ticket {

    private final Long id;

    @NotEmpty(message = "Please provide date of validity")
    private final LocalDate validDate;

    @NotEmpty(message = "Please provide user")
    private final User user;

    @NotEmpty(message = "Please provide Payment")
    private final Payment payment;

    @NotEmpty(message = "Please provide exposition")
    private final Exposition exposition;
}
