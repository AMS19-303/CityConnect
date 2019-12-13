package com.ams303.cityconnect.data;

import com.ams303.cityconnect.R;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String id;
    private List<OrderItem> items;
    private boolean active;
    private Courier courier;
    private int star_rating;
    private double total_price;
    private String timestamp;
    private String address;

    public Order(String id, List<OrderItem> items, boolean active, Courier courier, int star_rating, double total_price, String timestamp, String address) {
        this.id = id;
        this.items = items;
        this.active = active;
        this.courier = courier;
        this.star_rating = star_rating;
        this.total_price = total_price;
        this.timestamp = timestamp;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public boolean isActive() {
        return active;
    }

    public Courier getCourier() {
        return courier;
    }

    public int getStar_rating() {
        return star_rating;
    }

    public double getTotal_price() {
        return total_price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getAddress() {
        return address;
    }

    public String getStores() {
        List<String> stores = new ArrayList<>();
        for (OrderItem item : items) {
            String name = item.getStore_name();
            if (!stores.contains(name)) {
                stores.add(item.getStore_name());
            }
        }
        return stores.toString().substring(1, stores.toString().length()-1);
    }

    public int getDrawable() {
        switch(star_rating) {
            case 5:
                return R.drawable.rating_5_star;
            case 4:
                return R.drawable.rating_4_star;
            case 3:
                return R.drawable.rating_3_star;
            case 2:
                return R.drawable.rating_2_star;
            default:
                return R.drawable.rating_1_star;
        }
    }

    public boolean hasCourier() {
        return courier != null;
    }
}
