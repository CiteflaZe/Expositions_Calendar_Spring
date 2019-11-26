package com.project.calendar.service.mapper;

import com.project.calendar.domain.Exposition;
import com.project.calendar.domain.Payment;
import com.project.calendar.domain.User;
import com.project.calendar.entity.ExpositionEntity;
import com.project.calendar.entity.PaymentEntity;
import com.project.calendar.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentEntity mapPaymentToPaymentEntity(Payment payment){
        final ExpositionEntity expositionEntity = new ExpositionEntity();
        expositionEntity.setId(payment.getExposition().getId());

        final UserEntity userEntity = new UserEntity();
        userEntity.setId(payment.getUser().getId());

        final PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setPaymentTime(payment.getPaymentTime());
        paymentEntity.setStatus(payment.getStatus());
        paymentEntity.setPrice(payment.getPrice());
        paymentEntity.setTicketsAmount(payment.getTicketAmount());
        paymentEntity.setExposition(expositionEntity);
        paymentEntity.setUser(userEntity);

        return paymentEntity;
    }

    public Payment mapPaymentEntityToPayment(PaymentEntity entity){
        final Exposition exposition = Exposition.builder()
                .id(entity.getExposition().getId())
                .title(entity.getExposition().getTitle())
                .build();

        final User user = User.builder()
                .id(entity.getUser().getId())
                .build();

        return Payment.builder()
                .id(entity.getId())
                .paymentTime(entity.getPaymentTime())
                .price(entity.getPrice())
                .status(entity.getStatus())
                .ticketAmount(entity.getTicketsAmount())
                .exposition(exposition)
                .user(user)
                .build();
    }

}
