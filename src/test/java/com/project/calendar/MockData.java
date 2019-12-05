package com.project.calendar;

import com.project.calendar.domain.Exposition;
import com.project.calendar.domain.Hall;
import com.project.calendar.domain.Payment;
import com.project.calendar.domain.Role;
import com.project.calendar.domain.Status;
import com.project.calendar.domain.Ticket;
import com.project.calendar.domain.User;
import com.project.calendar.entity.ExpositionEntity;
import com.project.calendar.entity.HallEntity;
import com.project.calendar.entity.PaymentEntity;
import com.project.calendar.entity.TicketEntity;
import com.project.calendar.entity.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class MockData {
    private static final Long USER_ID = 4L;
    private static final String USER_EMAIL = "email@gmail.com";
    private static final String USER_PASSWORD = "userpass1";
    private static final String USER_NAME = "Name";
    private static final String USER_SURNAME = "Surname";
    private static final Role USER_ROLE = Role.USER;

    private static final Long HALL_ID = 4L;
    private static final String HALL_NAME = "Great Hall";
    private static final String HALL_CITY = "Kiev";
    private static final String HALL_STREET = "Some Street";
    private static final Integer HALL_HOUSE_NUMBER = 15;

    private static final Long EXPOSITION_ID = 5L;
    private static final String EXPOSITION_TITLE = "Generic Title";
    private static final String EXPOSITION_THEME = "Theme";
    private static final LocalDate EXPOSITION_START_DATE = LocalDate.of(2019, 11, 12);
    private static final LocalDate EXPOSITION_END_DATE = LocalDate.of(2019, 12, 13);
    private static final BigDecimal EXPOSITION_TICKET_PRICE = BigDecimal.valueOf(28.25);
    private static final String EXPOSITION_DESCRIPTION = "Description";

    private static final Long PAYMENT_ID = 9L;
    private static final LocalDateTime PAYMENT_TIME = LocalDateTime.of(2019, 11, 12, 14, 15, 16);
    private static final Status PAYMENT_STATUS = Status.PASSED;
    private static final BigDecimal PAYMENT_PRICE = BigDecimal.valueOf(804.25);
    private static final Integer PAYMENT_TICKETS_AMOUNT = 6;

    private static final Long TICKET_ID = 5L;
    private static final LocalDate TICKET_VALID_DATE = LocalDate.of(2019, 12, 13);

    public static final User MOCK_USER = initUser();
    public static final UserEntity MOCK_USER_ENTITY = initUserEntity();
    public static final Hall MOCK_HALL = initHall();
    public static final HallEntity MOCK_HALL_ENTITY = initHallEntity();
    public static final Exposition MOCK_EXPOSITION = initExposition();
    public static final ExpositionEntity MOCK_EXPOSITION_ENTITY = initExpositionEntity();
    public static final Payment MOCK_PAYMENT = initPayment();
    public static final PaymentEntity MOCK_PAYMENT_ENTITY = initPaymentEntity();
    public static final Ticket MOCK_TICKET = initTicket();
    public static final TicketEntity MOCK_TICKET_ENTITY = initTicketEntity();

    private static User initUser() {
        return User.builder()
                .id(USER_ID)
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .name(USER_NAME)
                .surname(USER_SURNAME)
                .role(USER_ROLE)
                .build();
    }

    private static UserEntity initUserEntity() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(USER_ID);
        userEntity.setEmail(USER_EMAIL);
        userEntity.setPassword(USER_PASSWORD);
        userEntity.setName(USER_NAME);
        userEntity.setSurname(USER_SURNAME);
        userEntity.setRole(USER_ROLE);

        return userEntity;
    }

    private static Hall initHall() {
        return Hall.builder()
                .id(HALL_ID)
                .name(HALL_NAME)
                .city(HALL_CITY)
                .street(HALL_STREET)
                .houseNumber(HALL_HOUSE_NUMBER)
                .build();
    }

    private static HallEntity initHallEntity() {
        final HallEntity hallEntity = new HallEntity();
        hallEntity.setId(HALL_ID);
        hallEntity.setName(HALL_NAME);
        hallEntity.setCity(HALL_CITY);
        hallEntity.setStreet(HALL_STREET);
        hallEntity.setHouseNumber(HALL_HOUSE_NUMBER);

        return hallEntity;
    }

    private static Exposition initExposition() {
        return Exposition.builder()
                .id(EXPOSITION_ID)
                .title(EXPOSITION_TITLE)
                .theme(EXPOSITION_THEME)
                .startDate(EXPOSITION_START_DATE)
                .endDate(EXPOSITION_END_DATE)
                .ticketPrice(EXPOSITION_TICKET_PRICE)
                .description(EXPOSITION_DESCRIPTION)
                .hall(MOCK_HALL)
                .build();
    }

    private static ExpositionEntity initExpositionEntity() {
        final ExpositionEntity expositionEntity = new ExpositionEntity();
        expositionEntity.setId(EXPOSITION_ID);
        expositionEntity.setTitle(EXPOSITION_TITLE);
        expositionEntity.setTheme(EXPOSITION_THEME);
        expositionEntity.setStartDate(EXPOSITION_START_DATE);
        expositionEntity.setEndDate(EXPOSITION_END_DATE);
        expositionEntity.setTicketPrice(EXPOSITION_TICKET_PRICE);
        expositionEntity.setDescription(EXPOSITION_DESCRIPTION);
        expositionEntity.setHall(MOCK_HALL_ENTITY);

        return expositionEntity;
    }

    private static Payment initPayment() {
        return Payment.builder()
                .id(PAYMENT_ID)
                .paymentTime(PAYMENT_TIME)
                .status(PAYMENT_STATUS)
                .price(PAYMENT_PRICE)
                .ticketsAmount(PAYMENT_TICKETS_AMOUNT)
                .exposition(MOCK_EXPOSITION)
                .user(MOCK_USER)
                .build();
    }

    private static PaymentEntity initPaymentEntity() {
        final PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(PAYMENT_ID);
        paymentEntity.setPaymentTime(PAYMENT_TIME);
        paymentEntity.setStatus(PAYMENT_STATUS);
        paymentEntity.setPrice(PAYMENT_PRICE);
        paymentEntity.setTicketsAmount(PAYMENT_TICKETS_AMOUNT);
        paymentEntity.setExposition(MOCK_EXPOSITION_ENTITY);
        paymentEntity.setUser(MOCK_USER_ENTITY);

        return paymentEntity;
    }

    private static Ticket initTicket() {
        return Ticket.builder()
                .id(TICKET_ID)
                .validDate(TICKET_VALID_DATE)
                .user(MOCK_USER)
                .exposition(MOCK_EXPOSITION)
                .payment(MOCK_PAYMENT)
                .build();
    }

    private static TicketEntity initTicketEntity() {
        final TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setId(TICKET_ID);
        ticketEntity.setValidDate(TICKET_VALID_DATE);
        ticketEntity.setUser(MOCK_USER_ENTITY);
        ticketEntity.setExposition(MOCK_EXPOSITION_ENTITY);
        ticketEntity.setPayment(MOCK_PAYMENT_ENTITY);

        return ticketEntity;
    }

}
