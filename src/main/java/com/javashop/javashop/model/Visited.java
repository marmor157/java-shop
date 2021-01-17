package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.signature.qual.Identifier;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(indexes = {
        @Index(columnList = "product_id", name = "visited_product_idx"),
        @Index(columnList = "user_id", name = "visited_user_idx")
})
public class Visited {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne
    Product product;

    @ManyToOne
    User user;

    @Column(name = "Date")
    private LocalDate date;

    public Visited(LocalDate date) {
        this.date = date;
    }
}





