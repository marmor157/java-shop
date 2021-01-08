package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="`Order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Date")
    private Date date;

    @Column(name = "Price")
    private Integer price;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "product_order",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<Product>();

    @ManyToOne()
    private User user;

    @ManyToOne()
    private DeliveryAddress deliveryAddress;

    @ManyToOne()
    private ShipmentMethod shipmentMethod;

    @OneToMany(mappedBy = "order")
    private List<Complaint> complaints;

    public Order(Date date, Integer price) {
        this.date = date;
        this.price = price;
    }
}