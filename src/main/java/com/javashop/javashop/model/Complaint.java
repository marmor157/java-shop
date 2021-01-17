package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Date")
    private LocalDate date;

    @ManyToOne()
    private ComplaintType complaintType;

    @ManyToOne()
    private User user;

    @ManyToOne()
    private Product product;

    @ManyToOne()
    private Order order;

    public Complaint(LocalDate date) {
        this.date = date;
    }
}
