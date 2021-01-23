package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="`Order`")
@Where(clause="delete_date is null")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Date")
    private LocalDate date;

    @Column(name = "Price")
    private Integer price;

    @Column(name = "Status")
    private String status;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

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
    private Set<Complaint> complaints = new HashSet<Complaint>();

    @OneToOne(mappedBy = "order")
    private OrderOpinion orderOpinion;

    public Order(LocalDate date, Integer price, String status) {
        this.date = date;
        this.price = price;
        this.status = status;
        this.deleteDate = null;
    }
}