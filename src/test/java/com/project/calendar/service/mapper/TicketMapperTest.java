package com.project.calendar.service.mapper;

import com.project.calendar.domain.Ticket;
import com.project.calendar.entity.TicketEntity;
import org.junit.Test;

import static com.project.calendar.MockData.MOCK_TICKET;
import static com.project.calendar.MockData.MOCK_TICKET_ENTITY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TicketMapperTest {

    private static final Ticket TICKET = MOCK_TICKET;
    private static final TicketEntity TICKET_ENTITY = MOCK_TICKET_ENTITY;

    private TicketMapper mapper = new TicketMapper();

    @Test
    public void mapTicketToTicketEntityShouldReturnTicketEntity() {
        final TicketEntity actual = mapper.mapTicketToTicketEntity(TICKET);

        assertThat(actual.getValidDate(), is(MOCK_TICKET.getValidDate()));
        assertThat(actual.getExposition().getId(), is(MOCK_TICKET.getExposition().getId()));
        assertThat(actual.getPayment().getId(), is(MOCK_TICKET.getPayment().getId()));
        assertThat(actual.getUser().getId(), is(MOCK_TICKET.getUser().getId()));
    }

    @Test
    public void mapTicketEntityToTicketShouldReturnTicket() {
        final Ticket actual = mapper.mapTicketEntityToTicket(TICKET_ENTITY);

        assertThat(actual.getId(), is(MOCK_TICKET_ENTITY.getId()));
        assertThat(actual.getValidDate(), is(MOCK_TICKET_ENTITY.getValidDate()));
        assertThat(actual.getExposition().getId(), is(MOCK_TICKET_ENTITY.getExposition().getId()));
        assertThat(actual.getExposition().getTitle(), is(MOCK_TICKET_ENTITY.getExposition().getTitle()));
        assertThat(actual.getExposition().getHall().getId(), is(MOCK_TICKET_ENTITY.getExposition().getHall().getId()));
        assertThat(actual.getExposition().getHall().getName(), is(MOCK_TICKET_ENTITY.getExposition().getHall().getName()));
        assertThat(actual.getExposition().getHall().getName(), is(MOCK_TICKET_ENTITY.getExposition().getHall().getName()));
        assertThat(actual.getPayment().getId(), is(MOCK_TICKET_ENTITY.getPayment().getId()));
        assertThat(actual.getUser().getId(), is(MOCK_TICKET_ENTITY.getUser().getId()));
    }

}