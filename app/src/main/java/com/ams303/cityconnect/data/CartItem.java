package com.ams303.cityconnect.data;

public class CartItem {
    private String name;
    public transient String type;

    public CartItem(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
