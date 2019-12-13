package com.ams303.cityconnect.data;

public class OrderItem {
    private Item product;
    private int quantity;
    private double cumul_price;
    private double discount;
    private String store_name;

    public OrderItem(Item product, int quantity, double cumul_price, double discount, String store_name) {
        this.product = product;
        this.quantity = quantity;
        this.cumul_price = cumul_price;
        this.discount = discount;
        this.store_name = store_name;
    }

    public Item getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCumul_price() {
        return cumul_price;
    }

    public double getDiscount() {
        return discount;
    }

    public String getStore_name() {
        return store_name;
    }
}
