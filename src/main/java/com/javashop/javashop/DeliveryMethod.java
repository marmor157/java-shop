package com.javashop.javashop;

public class DeliveryMethod {
    private int id;
    private String name;
    private int price;
    private int freeFrom;

    public DeliveryMethod(int id, String name, int price, int freeFrom) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.freeFrom = freeFrom;
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

    public int getFreeFrom() {
        return freeFrom;
    }

    public void setFreeFrom(int freeFrom) {
        this.freeFrom = freeFrom;
    }
}
