package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

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
    private List<Order> orders;

    public ShipmentMethod(String name, Integer price, Integer freeThreshold) {
        this.name = name;
        this.price = price;
        this.freeThreshold = freeThreshold;
    }
}