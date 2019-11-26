package com.project.calendar.repository;

import com.project.calendar.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ticketRepository")
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    List<TicketEntity> findAllByPaymentIdAndUserId(Long paymentId, Long userId);

    Optional<TicketEntity> findFirstByPaymentId(Long id);

    List<TicketEntity> findAllByUserId(Long id);
}
