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
public class ShipmentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Price")
    private Integer price;

    @Column(name = "freeThreshold")
    private Integer freeThreshold;

    @OneToMany(mappedBy = "shipmentMethod")
    private Set<Order> orders = new HashSet<Order>();

    public ShipmentMethod(String name, Integer price, Integer freeThreshold) {
        this.name = name;
        this.price = price;
        this.freeThreshold = freeThreshold;
    }
}