package com.project.calendar.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "expositions")
public class ExpositionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false, unique = true, length = 100)
    private String title;

    @Column(name = "theme", nullable = false, length = 100)
    private String theme;

    @Column(name = "start_time", nullable = false)
    private LocalDate startTime;

    @Column(name = "finish_time", nullable = false)
    private LocalDate finishTime;

    @Column(name = "ticket_price", nullable = false, scale = 2, precision = 6)
    private BigDecimal ticketPrice;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false)
    private HallEntity hall;

    @OneToMany(mappedBy = "exposition")
    private List<PaymentEntity> payments;

    @OneToMany(mappedBy = "exposition")
    private List<TicketEntity> tickets;
}