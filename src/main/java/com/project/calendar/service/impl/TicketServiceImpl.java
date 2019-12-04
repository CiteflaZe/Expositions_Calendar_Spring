package com.project.calendar.service.impl;

import com.project.calendar.domain.Ticket;
import com.project.calendar.entity.TicketEntity;
import com.project.calendar.exception.EntityNotFoundException;
import com.project.calendar.repository.TicketRepository;
import com.project.calendar.service.TicketService;
import com.project.calendar.service.mapper.TicketMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Service("ticketService")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Log4j
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;
    private TicketMapper ticketMapper;

    @Override
    public void add(Ticket ticket) {
        if (ticket == null) {
            log.warn("Ticket is null");
            throw new IllegalArgumentException("Ticket is null");
        }

        final TicketEntity ticketEntity = ticketMapper.mapTicketToTicketEntity(ticket);

        ticketRepository.save(ticketEntity);
    }

    @Override
    public Ticket showOneByPaymentId(Long id) {
        final Optional<TicketEntity> ticketEntity = ticketRepository.findFirstByPaymentId(id);

        return ticketEntity.map(ticketMapper::mapTicketEntityToTicket)
                .orElseThrow(() -> new EntityNotFoundException("No tickets found"));
    }

    public List<Ticket> showAllByPaymentIdAndUserId(Long paymentId, Long userId) {
        final List<TicketEntity> tickets = ticketRepository.findAllByPaymentIdAndUserId(paymentId, userId);
        return mapTicketEntityListToTicketList(tickets);
    }

    @Override
    public List<Ticket> showAllByUserId(Long id) {
        final List<TicketEntity> tickets = ticketRepository.findAllByUserId(id);

        return mapTicketEntityListToTicketList(tickets);
    }

    private List<Ticket> mapTicketEntityListToTicketList(List<TicketEntity> tickets) {
        return tickets.isEmpty() ?
                emptyList() :
                tickets.stream()
                        .map(ticketMapper::mapTicketEntityToTicket)
                        .collect(toList());
    }
}
