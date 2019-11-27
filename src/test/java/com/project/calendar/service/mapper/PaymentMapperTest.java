package com.project.calendar.service.mapper;

import com.project.calendar.domain.Exposition;
import com.project.calendar.domain.Payment;
import com.project.calendar.domain.Status;
import com.project.calendar.domain.User;
import com.project.calendar.entity.ExpositionEntity;
import com.project.calendar.entity.PaymentEntity;
import com.project.calendar.entity.UserEntity;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PaymentMapperTest {
    private static final Long ID = 9L;
    private static final LocalDateTime PAYMENT_TIME = LocalDateTime.of(2019, 11, 12, 14, 15, 16);
    private static final Status STATUS = Status.PASSED;
    private static final BigDecimal PRICE = BigDecimal.valueOf(804.25);
    private static final Integer TICKET_AMOUNT = 6;

    private static final Payment PAYMENT = initPayment();
    private static final PaymentEntity PAYMENT_ENTITY = initPaymentEntity();


    private PaymentMapper mapper = new PaymentMapper();

    @Test
    public void mapPaymentToPaymentEntityShouldReturn() {
        final PaymentEntity actual = mapper.mapPaymentToPaymentEntity(PAYMENT);

        assertThat(actual.getPaymentTime(), is(PAYMENT_TIME));
        assertThat(actual.getStatus(), is(STATUS));
        assertThat(actual.getPrice(), is(PRICE));
        assertThat(actual.getTicketsAmount(), is(TICKET_AMOUNT));
        assertThat(actual.getUser(), is(notNullValue()));
        assertThat(actual.getExposition(), is(notNullValue()));
    }

    @Test
    public void mapPaymentEntityToPayment() {
        final Payment actual = mapper.mapPaymentEntityToPayment(PAYMENT_ENTITY);

        assertThat(actual.getId(), is(ID));
        assertThat(actual.getPaymentTime(), is(PAYMENT_TIME));
        assertThat(actual.getStatus(), is(STATUS));
        assertThat(actual.getPrice(), is(PRICE));
        assertThat(actual.getTicketAmount(), is(TICKET_AMOUNT));
        assertThat(actual.getUser(), is(notNullValue()));
        assertThat(actual.getExposition(), is(notNullValue()));
    }

    private static Payment initPayment() {
        final User user = User.builder()
                .id(1L)
                .build();
        final Exposition exposition = Exposition.builder()
                .id(5L)
                .build();

        return Payment.builder()
                .paymentTime(PAYMENT_TIME)
                .status(STATUS)
                .price(PRICE)
                .ticketAmount(TICKET_AMOUNT)
                .user(user)
                .exposition(exposition)
                .build();
    }

    private static PaymentEntity initPaymentEntity() {
        final UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        final ExpositionEntity expositionEntity = new ExpositionEntity();
        expositionEntity.setId(5L);

        final PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(ID);
        paymentEntity.setPaymentTime(PAYMENT_TIME);
        paymentEntity.setStatus(STATUS);
        paymentEntity.setPrice(PRICE);
        paymentEntity.setTicketsAmount(TICKET_AMOUNT);
        paymentEntity.setUser(userEntity);
        paymentEntity.setExposition(expositionEntity);

        return paymentEntity;
    }

}