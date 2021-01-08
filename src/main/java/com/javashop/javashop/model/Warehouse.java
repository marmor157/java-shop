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
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Address")
    private String address;

    @OneToMany(mappedBy = "warehouse")
    private Set<ProductWarehouse> products = new HashSet<ProductWarehouse>();

    public Warehouse(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
