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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Price")
    private Integer price;

    @Column(name = "DiscountPrice")
    private Integer discountPrice;

    @Column(name = "NoAvailable")
    private Integer noAvailable;

    @Column(name = "Description")
    private String description;

    @Column(name = "ImagePath")
    private String imagePath;

    @ManyToOne()
    private TaxCategory taxCategory;

    @OneToMany(mappedBy = "product")
    private List<ProductWarehouse> warehouses;

    @OneToMany(mappedBy = "product")
    private List<ProductSupplier> suppliers;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<Category>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "products_subcategories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "subcategory_id")
    )
    private Set<Subcategory> subcategories = new HashSet<Subcategory>();

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "products")
    private Set<Order> orders = new HashSet<Order>();

    @OneToMany(mappedBy = "product")
    private List<Visited> visited;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "wishlistUser")
    private Set<User> wishlistProduct = new HashSet<User>();

    @OneToMany(mappedBy = "product")
    private List<Opinion> opinions;

    @OneToMany(mappedBy = "user")
    private List<Complaint> complaints;

    public Product(String name, Integer price, Integer discountPrice, Integer noAvailable, String description, String imagePath, TaxCategory taxCategory) {
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
        this.noAvailable = noAvailable;
        this.description = description;
        this.imagePath = imagePath;
        this.taxCategory = taxCategory;
    }
}
