package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DeliveryAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Surname")
    private String surname;

    @Column(name = "City")
    private String city;

    @Column(name = "Street")
    private String street;

    @Column(name = "buildingNumber")
    private String buildingNumber;

    @Column(name = "postCode")
    private String postCode;

    @ManyToOne()
    private User user;

    @OneToMany(mappedBy = "deliveryAddress")
    private List<Order> orders;

    public DeliveryAddress(String name, String surname, String city, String street, String buildingNumber, String postCode) {
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;
        this.postCode = postCode;
    }
}