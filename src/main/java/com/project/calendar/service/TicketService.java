package com.project.calendar.service;

import com.project.calendar.domain.Ticket;

import java.util.List;

public interface TicketService {
    void add(Ticket ticket);

    List<Ticket> showByUserId(Long id);

    Ticket showOneByPaymentId(Long id);

    List<Ticket> showAllByPaymentIdAndUserId(Long paymentId, Long userId);
}