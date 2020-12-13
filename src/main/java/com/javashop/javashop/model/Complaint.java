package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Complaint")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Date")
    private Date date;

    @Column(name = "ComplaintTypeId")
    private Integer complaintTypeId;

    public Complaint(Date date, Integer complaintTypeId) {
        this.date = date;
        this.complaintTypeId = complaintTypeId;
    }
}
