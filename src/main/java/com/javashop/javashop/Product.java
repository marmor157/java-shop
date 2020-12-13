package com.javashop.javashop;

public class Product {
    private int id;
    private String name;
    private int price;
    private int numberAvailable;
    private String description;
    private int salePrice;
    private  String imageSrc;
    private int taxCategoryID;


    public Product(int id, String name, int price, int numberAvailable, String description, int salePrice, String imageSrc, int taxCategoryID) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.numberAvailable = numberAvailable;
        this.description = description;
        this.salePrice = salePrice;
        this.imageSrc = imageSrc;
        this.taxCategoryID = taxCategoryID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberAvailable() {
        return numberAvailable;
    }

    public void setNumberAvailable(int numberAvailable) {
        this.numberAvailable = numberAvailable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public int getTaxCategoryID() {
        return taxCategoryID;
    }

    public void setTaxCategoryID(int taxCategoryID) {
        this.taxCategoryID = taxCategoryID;
    }
}
