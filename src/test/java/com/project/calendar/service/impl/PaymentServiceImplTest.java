package com.project.calendar.service.impl;

import com.project.calendar.domain.Payment;
import com.project.calendar.entity.PaymentEntity;
import com.project.calendar.exception.EntityNotFoundException;
import com.project.calendar.repository.PaymentRepository;
import com.project.calendar.service.mapper.PaymentMapper;
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

import static com.project.calendar.MockData.MOCK_PAYMENT;
import static com.project.calendar.MockData.MOCK_PAYMENT_ENTITY;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = PaymentServiceImpl.class)
public class PaymentServiceImplTest {

    private static final Payment PAYMENT = MOCK_PAYMENT;
    private static final PaymentEntity PAYMENT_ENTITY = MOCK_PAYMENT_ENTITY;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @MockBean
    private PaymentRepository paymentRepository;

    @MockBean
    private PaymentMapper paymentMapper;

    @Autowired
    private PaymentServiceImpl paymentService;

    @After
    public void resetMocks(){
        reset(paymentRepository, paymentMapper);
    }

    @Test
    public void addShouldThrowIllegalArgumentException() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Payment is null");

        paymentService.add(null);
    }

    @Test
    public void addShouldAddPayment() {
        when(paymentMapper.mapPaymentToPaymentEntity(any(Payment.class))).thenReturn(PAYMENT_ENTITY);

        paymentService.add(PAYMENT);

        verify(paymentRepository).save(PAYMENT_ENTITY);
    }

    @Test
    public void showAllByUserIdShouldReturnEmptyList() {
        when(paymentRepository.findAllByUserId(anyLong())).thenReturn(emptyList());

        final List<Payment> actual = paymentService.showAllByUserId(4L);

        assertThat(actual, is(emptyList()));
    }

    @Test
    public void showAllByUserIdShouldReturnPaymentsList() {
        when(paymentRepository.findAllByUserId(anyLong())).thenReturn(singletonList(PAYMENT_ENTITY));
        when(paymentMapper.mapPaymentEntityToPayment(any(PaymentEntity.class))).thenReturn(PAYMENT);

        final List<Payment> actual = paymentService.showAllByUserId(5L);

        assertThat(actual, is(not(emptyList())));
        assertThat(actual, hasItem(PAYMENT));
    }

    @Test
    public void showLastByUserIdShouldThrowInvalidEntityException(){
        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage("No payments found");

        when(paymentRepository.findFirstByUserIdOrderByIdDesc(anyLong())).thenReturn(Optional.empty());

        paymentService.showLastByUserId(4L);
    }

    @Test
    public void showLastByUserIdShouldReturnPayment(){
        when(paymentRepository.findFirstByUserIdOrderByIdDesc(anyLong())).thenReturn(Optional.of(PAYMENT_ENTITY));
        when(paymentMapper.mapPaymentEntityToPayment(any(PaymentEntity.class))).thenReturn(PAYMENT);

        final Payment actual = paymentService.showLastByUserId(9L);

        assertThat(actual, is(PAYMENT));
    }
}