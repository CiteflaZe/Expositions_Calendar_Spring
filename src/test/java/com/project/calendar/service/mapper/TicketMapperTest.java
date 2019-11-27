package com.project.calendar.service.mapper;

import com.project.calendar.domain.Ticket;
import com.project.calendar.entity.TicketEntity;

import java.time.LocalDate;

public class TicketMapperTest {
    private static final LocalDate VALID_DATE = LocalDate.of(2019, 12, 13);
    private static final Long USER_ID = 1L;
    private static final Long PAYMENT_ID = 2L;
    private static final Long EXPOSITION_ID = 3L;

//    private static final Ticket TICKET;
//    private static final TicketEntity TICKET_ENTITY;

    private TicketMapper ticketMapper = new TicketMapper();


    private static Ticket initTicket(){


        return Ticket.builder()

                .build();
    }

}