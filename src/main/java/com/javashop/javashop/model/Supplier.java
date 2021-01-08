package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Telephone")
    private String telephone;

    @OneToMany(mappedBy = "supplier")
    private Set<ProductSupplier> products = new HashSet<ProductSupplier>();

    public Supplier(String name, String telephone) {
        this.name = name;
        this.telephone = telephone;
    }
}
