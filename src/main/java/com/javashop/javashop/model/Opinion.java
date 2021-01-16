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
@IdClass(OpinionId.class)
public class Opinion {
    @Id
    @ManyToOne
    Product product;

    @Id
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

class OpinionId implements Serializable {
    int product;
    int user;
}


