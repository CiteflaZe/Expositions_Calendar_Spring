package com.project.calendar.repository;

import com.project.calendar.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ticketRepository")
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    List<TicketEntity> findByPaymentId(Long id);
}