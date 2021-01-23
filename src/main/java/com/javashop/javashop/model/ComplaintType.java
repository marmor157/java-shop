package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Where(clause="delete_date is null")
public class ComplaintType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @OneToMany(mappedBy = "complaintType")
    private Set<Complaint> complaints = new HashSet<Complaint>();

    public ComplaintType(String name) {
        this.name = name;
        this.deleteDate = null;
    }
}
