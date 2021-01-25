package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Where(clause="delete_date is null")
public class OrderOpinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "MarkShipment")
    private Integer markShipment;

    @Column(name = "MarkTime")
    private Integer markTime;

    @Column(name = "MarkCustomerService")
    private Integer markCustomerService;

    @Column(name = "Date")
    private LocalDate date;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @OneToOne()
    private Order order;

    @ManyToOne
    private User user;

    public OrderOpinion(Integer markShipment, Integer markTime, Integer markCustomerService, LocalDate date) {
        this.markShipment = markShipment;
        this.markTime = markTime;
        this.markCustomerService = markCustomerService;
        this.date = date;
        this.deleteDate = null;
    }
}



