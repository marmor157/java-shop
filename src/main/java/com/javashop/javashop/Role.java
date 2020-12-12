package com.javashop.javashop;

public class Role {
    private int ID;
    private String name;

    public Role(int _ID, String _name){
        this.name = _name;
        this.ID = _ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
