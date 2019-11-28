package com.project.calendar.service.impl;

import com.project.calendar.domain.Payment;
import com.project.calendar.entity.PaymentEntity;
import com.project.calendar.exception.InvalidEntityException;
import com.project.calendar.repository.PaymentRepository;
import com.project.calendar.service.PaymentService;
import com.project.calendar.service.mapper.PaymentMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.*;
import static java.util.stream.Collectors.*;

@Service("paymentService")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Log4j
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;
    private PaymentMapper paymentMapper;

    @Override
    public void add(Payment payment) {
        if(payment == null){
            log.warn("Payment is null");
            throw new IllegalArgumentException("Payment is null");
        }

        final PaymentEntity paymentEntity = paymentMapper.mapPaymentToPaymentEntity(payment);

        paymentRepository.save(paymentEntity);
    }

    @Override
    public List<Payment> showAllByUserId(Long id) {
        final List<PaymentEntity> payments = paymentRepository.findAllByUserId(id);
        return payments.isEmpty() ? emptyList() :
                payments.stream()
                        .map(paymentMapper::mapPaymentEntityToPayment)
                        .collect(toList());
    }

    @Override
    public Payment showLastByUserId(Long id) {
        final Optional<PaymentEntity> paymentEntity = paymentRepository.findFirstByUserIdOrderByIdDesc(id);

        return paymentEntity
                .map(paymentMapper::mapPaymentEntityToPayment)
                .orElseThrow(() -> new InvalidEntityException("No payments found"));

    }
}
