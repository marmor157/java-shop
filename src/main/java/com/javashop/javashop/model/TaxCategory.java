package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TaxCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "TaxRate")
    private Integer taxRate;

    @OneToMany(mappedBy = "taxCategory")
    private Set<Product> products = new HashSet<Product>();

    public TaxCategory(String name, Integer taxRate) {
        this.name = name;
        this.taxRate = taxRate;
    }

}
