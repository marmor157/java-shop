package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Where(clause="delete_date is null")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Date")
    private LocalDate date;

    @Column(name = "Status")
    private String status;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @ManyToOne()
    private ComplaintType complaintType;

    @ManyToOne()
    private User user;

    @ManyToOne()
    private Product product;

    @ManyToOne()
    private Order order;

    public Complaint(LocalDate date, String status) {
        this.date = date;
        this.status = status;
        this.deleteDate = null;
    }
}
