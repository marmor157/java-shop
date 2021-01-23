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
public class ProductWarehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @ManyToOne Product product;

    @ManyToOne Warehouse warehouse;

    @Column(name = "Quantity")
    private Integer quantity;

    public ProductWarehouse(Integer quantity) {
        this.quantity = quantity;
        this.deleteDate = null;
    }
}



