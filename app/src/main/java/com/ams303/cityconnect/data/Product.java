package com.ams303.cityconnect.data;

public class Product extends CartItem {
    private int quantity;
    private double price;
    private String store;
    private String unit;

    public Product(String name, int quantity, double price, String store, String unit) {
        super(name, "Product");
        this.quantity = quantity;
        this.price = price;
        this.store = store;
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public String getStore() {
        return store;
    }

    public String getUnit() {
        return unit;
    }
}
