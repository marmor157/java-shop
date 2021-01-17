package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ProductWarehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne Product product;

    @ManyToOne Warehouse warehouse;

    @Column(name = "Quantity")
    private Integer quantity;

    public ProductWarehouse(Integer quantity) {
        this.quantity = quantity;
    }
}



