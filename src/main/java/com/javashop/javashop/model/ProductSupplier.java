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
@IdClass(ProductSupplierId.class)
public class ProductSupplier {
    @Id
    @ManyToOne
    Product product;

    @Id
    @ManyToOne Supplier supplier;

    @Column(name = "Price")
    private Integer price;

    public ProductSupplier(Integer price) {
        this.price = price;
    }
}

class ProductSupplierId implements Serializable {
    int product;
    int supplier;
}


