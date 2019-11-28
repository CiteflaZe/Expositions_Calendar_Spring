package com.project.calendar.service.impl;

import com.project.calendar.domain.Ticket;
import com.project.calendar.entity.TicketEntity;
import com.project.calendar.exception.InvalidEntityException;
import com.project.calendar.repository.TicketRepository;
import com.project.calendar.service.mapper.TicketMapper;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static com.project.calendar.MockData.MOCK_TICKET;
import static com.project.calendar.MockData.MOCK_TICKET_ENTITY;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TicketServiceImpl.class)
public class TicketServiceImplTest {

    private static final Ticket TICKET = MOCK_TICKET;
    private static final TicketEntity TICKET_ENTITY = MOCK_TICKET_ENTITY;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private TicketMapper ticketMapper;

    @Autowired
    private TicketServiceImpl ticketService;

    @After
    public void resetMock() {
        reset(ticketRepository, ticketMapper);
    }

    @Test
    public void addShouldThrowIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Ticket is null");

        ticketService.add(null);
    }

    @Test
    public void addShouldReturnTicket() {
        when(ticketMapper.mapTicketToTicketEntity(any(Ticket.class))).thenReturn(TICKET_ENTITY);

        ticketService.add(TICKET);

        verify(ticketRepository).save(TICKET_ENTITY);
    }

    @Test
    public void showOneByPaymentIdShouldThrowInvalidEntityException() {
        expectedException.expect(InvalidEntityException.class);
        expectedException.expectMessage("No tickets found");

        when(ticketRepository.findFirstByPaymentId(anyLong())).thenReturn(Optional.empty());

        ticketService.showOneByPaymentId(4L);
    }

    @Test
    public void showOneByPaymentIdShouldReturnTicket() {
        when(ticketRepository.findFirstByPaymentId(anyLong())).thenReturn(Optional.of(TICKET_ENTITY));
        when(ticketMapper.mapTicketEntityToTicket(any(TicketEntity.class))).thenReturn(TICKET);

        final Ticket actual = ticketService.showOneByPaymentId(4L);

        assertThat(actual, is(TICKET));
    }

    @Test
    public void showAllByPaymentIdAndUserIdShouldReturnEmptyEmptyList() {
        when(ticketRepository.findAllByPaymentIdAndUserId(anyLong(), anyLong())).thenReturn(emptyList());

        final List<Ticket> actual = ticketService.showAllByPaymentIdAndUserId(1L, 2L);

        assertThat(actual, is(emptyList()));
    }

    @Test
    public void showAllByPaymentIdAndUserIdShouldReturnTicketsList() {
        when(ticketRepository.findAllByPaymentIdAndUserId(anyLong(), anyLong())).thenReturn(singletonList(TICKET_ENTITY));
        when(ticketMapper.mapTicketEntityToTicket(any(TicketEntity.class))).thenReturn(TICKET);

        final List<Ticket> actual = ticketService.showAllByPaymentIdAndUserId(2L, 4L);

        assertThat(actual, is(not(emptyList())));
        assertThat(actual, hasItem(TICKET));

    }
}