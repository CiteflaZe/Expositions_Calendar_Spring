package com.project.calendar.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Payment {

    private final Long id;

    @NotEmpty(message = "Please provide payment time")
    private final LocalDateTime paymentTime;

    @NotEmpty(message = "Please provide status")
    private final Status status;

    @NotEmpty(message = "Please provide amount of tickets")
    private final Integer ticketAmount;

    @NotEmpty(message = "Please provide price")
    private final BigDecimal price;

    @NotEmpty(message = "Please provide user")
    private final User user;

    @NotEmpty(message = "Please provide exposition")
    private final Exposition exposition;
}
