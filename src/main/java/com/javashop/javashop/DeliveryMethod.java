package com.javashop.javashop;

public class DeliveryMethod {
    private int id;
    private String name;
    private int price;
    private int freeThreshold;

    public DeliveryMethod(int id, String name, int price, int freeThreshold) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.freeThreshold = freeThreshold;
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

    public int getFreeThreshold() {
        return freeThreshold;
    }

    public void setFreeThreshold(int freeThreshold) {
        this.freeThreshold = freeThreshold;
    }
}
