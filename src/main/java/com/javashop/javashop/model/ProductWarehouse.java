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
@IdClass(ProductWarehouseId.class)
public class ProductWarehouse {
    @Id
    @ManyToOne Product product;

    @Id
    @ManyToOne Warehouse warehouse;

    @Column(name = "Quantity")
    private Integer quantity;

    public ProductWarehouse(Integer quantity) {
        this.quantity = quantity;
    }
}

class ProductWarehouseId implements Serializable {
    int product;
    int warehouse;
}


