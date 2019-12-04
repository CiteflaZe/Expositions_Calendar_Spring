package com.project.calendar.repository;

import com.project.calendar.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ticketRepository")
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    List<TicketEntity> findAllByPaymentIdAndUserId(Long paymentId, Long userId);

    Optional<TicketEntity> findFirstByPaymentId(Long id);

    @Query("SELECT t FROM TicketEntity t WHERE t.user.id = ?1 GROUP BY t.payment")
    List<TicketEntity> findAllByUserId(Long id);
}
