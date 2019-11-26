package com.project.calendar.service;

import com.project.calendar.domain.Payment;

import java.util.List;

public interface PaymentService {
    void add(Payment payment);

    List<Payment> showAllByUserId(Long id);

    Payment showLastByUserId(Long id);
}
