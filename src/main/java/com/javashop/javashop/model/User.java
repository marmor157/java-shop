package com.javashop.javashop.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Where(clause="delete_date is null")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Login", unique = true)
    private String login;

    @Column(name = "Password")
    private String password;

    @Column(name = "Email", unique = true)
    private String email;

    @Column(name = "Name")
    private String name;

    @Column(name = "Surname")
    private String surname;

    @Column(name = "Address")
    private String address;

    @Column(name = "BirthDate", columnDefinition = "DATE")
    private LocalDate birthDate;

    @Column(name = "Telephone")
    private String telephone;

    @Column(name = "DeleteDate")
    private LocalDate deleteDate;

    @ManyToOne()
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "wishlist",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> wishlist = new HashSet<Product>();

    @OneToMany(mappedBy = "user")
    private Set<Visited> visited= new HashSet<Visited>();

    @OneToMany(mappedBy = "user")
    private Set<Opinion> opinions= new HashSet<Opinion>();

    @OneToMany(mappedBy = "user")
    private Set<Complaint> complaints= new HashSet<Complaint>();

    @OneToMany(mappedBy = "user")
    private Set<Order> orders= new HashSet<Order>();

    @OneToMany(mappedBy = "user")
    private Set<DeliveryAddress> deliveryAddresses= new HashSet<DeliveryAddress>();

    @OneToMany(mappedBy = "user")
    private Set<OrderOpinion> orderOpinions = new HashSet<OrderOpinion>();

    public User(String login, String password, String email, String name, String surname, String address, LocalDate birthDate, String telephone) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.birthDate = birthDate;
        this.telephone = telephone;
        this.deleteDate = null;
    }

}
