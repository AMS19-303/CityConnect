package com.ams303.cityconnect.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.ams303.cityconnect.R;

import java.util.ArrayList;
import java.util.List;

public class Order implements Parcelable {
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
            if (!stores.contains(name) && item.isProduct()) {
                stores.add(item.getStore_name());
            }
        }
        return stores.toString().substring(1, stores.toString().length()-1);
    }

    public List<Integer> getStoreIDs() {
        List<Integer> stores = new ArrayList<>();
        for (OrderItem item : items) {
            if (item.isProduct()) {
                stores.add(item.getProduct().getStore_id());
            }
        }
        return stores;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeList(items);
        out.writeByte((byte) (active ? 1 : 0));
        out.writeParcelable(courier, flags);
        out.writeInt(star_rating);
        out.writeDouble(total_price);
        out.writeString(timestamp);
        out.writeString(address);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    private Order(Parcel in) {
        id = in.readString();
        items = in.readArrayList(OrderItem.class.getClassLoader());
        active = in.readByte() != 0;
        courier = in.readParcelable(Courier.class.getClassLoader());
        star_rating = in.readInt();
        total_price = in.readDouble();
        timestamp = in.readString();
        address = in.readString();
    }
}
