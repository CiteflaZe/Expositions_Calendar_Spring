package com.project.calendar.service.impl;

import com.project.calendar.domain.Ticket;
import com.project.calendar.entity.TicketEntity;
import com.project.calendar.repository.TicketRepository;
import com.project.calendar.service.TicketService;
import com.project.calendar.service.mapper.TicketMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service("ticketService")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Log4j
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;
    private TicketMapper mapper;

    @Override
    public void add(Ticket ticket) {
        if (ticket == null) {
            log.warn("Ticket is null");
            throw new IllegalArgumentException("Ticket is null");
        }

        final TicketEntity ticketEntity = mapper.mapTicketToTicketEntity(ticket);

        ticketRepository.save(ticketEntity);

    }

    @Override
    public Ticket showOneByPaymentId(Long id) {
        final Optional<TicketEntity> ticketEntity = ticketRepository.findFirstByPaymentId(id);

        return ticketEntity.map(mapper::mapTicketEntityToTicket).orElse(null);
    }

    public List<Ticket> showAllByPaymentIdAndUserId(Long paymentId, Long userId) {
        final List<TicketEntity> tickets = ticketRepository.findAllByPaymentIdAndUserId(paymentId, userId);
        return mapTicketEntityListToTicketList(tickets);
    }

    private List<Ticket> mapTicketEntityListToTicketList(List<TicketEntity> tickets) {
        return tickets.isEmpty() ? emptyList() :
                tickets.stream()
                        .map(mapper::mapTicketEntityToTicket)
                        .collect(Collectors.toList());
    }
}
