package com.project.calendar.repository;

import com.project.calendar.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("paymentRepository")
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    Optional<PaymentEntity> findFirstByUserIdOrderByIdDesc(Long id);

    List<PaymentEntity> findAllByUserId(Long id);

}
