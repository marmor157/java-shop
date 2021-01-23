package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Where(clause="delete_date is null")
public class ProductSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @ManyToOne
    Product product;

    @ManyToOne Supplier supplier;

    @Column(name = "Price")
    private Integer price;

    public ProductSupplier(Integer price) {
        this.price = price;
        this.deleteDate = null;
    }
}



