package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    Product product;

    @ManyToOne User user;

    @Column(name = "Mark")
    private Integer mark;

    @Column(name = "Text")
    private String text;

    @Column(name = "Date")
    private LocalDate date;

    public Opinion(Integer mark, String text, LocalDate date) {
        this.mark = mark;
        this.text = text;
        this.date = date;
    }
}



