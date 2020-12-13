package com.javashop.javashop;

public class TaxCategory {
    private int id;
    private String name;
    private int taxRate;

    public TaxCategory(int id, String name, int taxRate) {
        this.id = id;
        this.name = name;
        this.taxRate = taxRate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTaxRate() {
        return taxRate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTaxRate(int taxRate) {
        this.taxRate = taxRate;
    }
}
