package com.project.calendar.service.mapper;

import com.project.calendar.domain.Exposition;
import com.project.calendar.domain.Hall;
import com.project.calendar.domain.Payment;
import com.project.calendar.domain.Ticket;
import com.project.calendar.domain.User;
import com.project.calendar.entity.ExpositionEntity;
import com.project.calendar.entity.PaymentEntity;
import com.project.calendar.entity.TicketEntity;
import com.project.calendar.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public TicketEntity mapTicketToTicketEntity(Ticket ticket){
        final ExpositionEntity expositionEntity = new ExpositionEntity();
        expositionEntity.setId(ticket.getExposition().getId());

        final PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(ticket.getPayment().getId());

        final UserEntity userEntity = new UserEntity();
        userEntity.setId(ticket.getUser().getId());

        final TicketEntity ticketEntity = new TicketEntity();
        ticketEntity.setValidDate(ticket.getValidDate());
        ticketEntity.setExposition(expositionEntity);
        ticketEntity.setPayment(paymentEntity);
        ticketEntity.setUser(userEntity);
        return ticketEntity;
    }

    public Ticket mapTicketEntityToTicket(TicketEntity entity){
        final Hall hall = Hall.builder()
                .name(entity.getExposition().getHall().getName())
                .build();

        final Exposition exposition = Exposition.builder()
                .id(entity.getExposition().getId())
                .title(entity.getExposition().getTitle())
                .hall(hall)
                .build();

        final Payment payment = Payment.builder()
                .id(entity.getPayment().getId())
                .build();

        final User user = User.builder()
                .id(entity.getUser().getId())
                .build();

        return Ticket.builder()
                .id(entity.getId())
                .validDate(entity.getValidDate())
                .exposition(exposition)
                .payment(payment)
                .user(user)
                .build();
    }

}
