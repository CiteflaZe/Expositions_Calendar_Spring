package com.project.calendar.service.mapper;

import com.project.calendar.domain.Payment;
import com.project.calendar.entity.PaymentEntity;
import org.junit.Test;

import static com.project.calendar.MockData.MOCK_PAYMENT;
import static com.project.calendar.MockData.MOCK_PAYMENT_ENTITY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PaymentMapperTest {

    private static final Payment PAYMENT = MOCK_PAYMENT;
    private static final PaymentEntity PAYMENT_ENTITY = MOCK_PAYMENT_ENTITY;

    private final PaymentMapper paymentMapper = new PaymentMapper();

    @Test
    public void mapPaymentToPaymentEntityShouldReturnPaymentEntity() {
        final PaymentEntity actual = paymentMapper.mapPaymentToPaymentEntity(PAYMENT);

        assertThat(actual.getPaymentTime(), is(MOCK_PAYMENT.getPaymentTime()));
        assertThat(actual.getStatus(), is(MOCK_PAYMENT.getStatus()));
        assertThat(actual.getPrice(), is(MOCK_PAYMENT.getPrice()));
        assertThat(actual.getTicketsAmount(), is(MOCK_PAYMENT.getTicketsAmount()));
        assertThat(actual.getUser().getId(), is(MOCK_PAYMENT.getUser().getId()));
        assertThat(actual.getExposition().getId(), is(MOCK_PAYMENT.getExposition().getId()));
    }

    @Test
    public void mapPaymentEntityToPaymentShouldReturnPayment() {
        final Payment actual = paymentMapper.mapPaymentEntityToPayment(PAYMENT_ENTITY);

        assertThat(actual.getId(), is(PAYMENT_ENTITY.getId()));
        assertThat(actual.getPaymentTime(), is(PAYMENT_ENTITY.getPaymentTime()));
        assertThat(actual.getStatus(), is(PAYMENT_ENTITY.getStatus()));
        assertThat(actual.getPrice(), is(PAYMENT_ENTITY.getPrice()));
        assertThat(actual.getTicketsAmount(), is(PAYMENT_ENTITY.getTicketsAmount()));
        assertThat(actual.getUser().getId(), is(PAYMENT_ENTITY.getUser().getId()));
        assertThat(actual.getExposition().getId(), is(PAYMENT_ENTITY.getExposition().getId()));
    }

}