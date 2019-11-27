package com.project.calendar.service.mapper;

import com.project.calendar.domain.Exposition;
import com.project.calendar.domain.Payment;
import com.project.calendar.domain.Ticket;
import com.project.calendar.domain.User;
import com.project.calendar.entity.ExpositionEntity;
import com.project.calendar.entity.HallEntity;
import com.project.calendar.entity.PaymentEntity;
import com.project.calendar.entity.TicketEntity;
import com.project.calendar.entity.UserEntity;
import org.junit.Test;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

public class TicketMapperTest {
    private static final LocalDate VALID_DATE = LocalDate.of(2019, 12, 13);
    private static final Long USER_ID = 1L;
    private static final Long PAYMENT_ID = 2L;
    private static final Long EXPOSITION_ID = 3L;
    private static final Long HALL_ID = 4L;
    private static final String EXPOSITION_TITLE = "Expo Title";
    private static final String HALL_NAME = "Great Hall";

    private static final Ticket TICKET = initTicket();
    private static final TicketEntity TICKET_ENTITY = initTicketEntity();

    private TicketMapper mapper = new TicketMapper();

    @Test
    public void mapTicketToTicketEntityShouldReturnTicketEntity() {
        final TicketEntity actual = mapper.mapTicketToTicketEntity(TICKET);

        assertThat(actual.getValidDate(), is(VALID_DATE));
        assertThat(actual.getExposition().getId(), is(EXPOSITION_ID));
        assertThat(actual.getPayment().getId(), is(PAYMENT_ID));
        assertThat(actual.getUser().getId(), is(USER_ID));
    }

    @Test
    public void mapTicketEntityToTicketShouldReturnTicket(){
        final Ticket actual = mapper.mapTicketEntityToTicket(TICKET_ENTITY);

        assertThat(actual.getValidDate(), is(VALID_DATE));
        assertThat(actual.getExposition().getId(), is(EXPOSITION_ID));
        assertThat(actual.getExposition().getTitle(), is(EXPOSITION_TITLE));
        assertThat(actual.getExposition().getHall().getId(), is(HALL_ID));
        assertThat(actual.getExposition().getHall().getName(), is(HALL_NAME));
        assertThat(actual.getPayment().getId(), is(PAYMENT_ID));
        assertThat(actual.getUser().getId(), is(USER_ID));
    }

    private static Ticket initTicket() {
        final Exposition exposition = Exposition.builder()
                .id(EXPOSITION_ID)
                .build();
        final User user = User.builder()
                .id(USER_ID)
                .build();
        final Payment payment = Payment.builder()
                .id(PAYMENT_ID)
                .build();

        return Ticket.builder()
                .validDate(VALID_DATE)
                .user(user)
                .exposition(exposition)
                .payment(payment)
                .build();
    }

    private static TicketEntity initTicketEntity() {
        final HallEntity hallEntity = new HallEntity();
        hallEntity.setId(HALL_ID);
        hallEntity.setName(HALL_NAME);

        final ExpositionEntity expositionEntity = new ExpositionEntity();
        expositionEntity.setId(EXPOSITION_ID);
        expositionEntity.setTitle(EXPOSITION_TITLE);
        expositionEntity.setHall(hallEntity);

        final UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);

        final PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(PAYMENT_ID);

        final TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setValidDate(VALID_DATE);
        ticketEntity.setUser(userEntity);
        ticketEntity.setPayment(paymentEntity);
        ticketEntity.setExposition(expositionEntity);

        return ticketEntity;
    }

}