package com.ams303.cityconnect.data;

public class Product extends CartItem {
    private int id;
    private int quantity;
    private double price;
    private String store;
    private String unit;

    public Product(int id, String name, int quantity, double price, String store, String unit) {
        super(name, "Product");
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.store = store;
        this.unit = unit;
    }

    public int getId() {
        return id;
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
