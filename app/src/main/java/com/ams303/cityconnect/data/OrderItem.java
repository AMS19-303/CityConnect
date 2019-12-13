package com.ams303.cityconnect.data;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderItem implements Parcelable {
    private Item product;
    private int quantity;
    private double cumul_price;
    private double discount;
    private String store_name;
    private String request;

    public OrderItem(Item product, int quantity, double cumul_price, double discount, String store_name, String request) {
        this.product = product;
        this.quantity = quantity;
        this.cumul_price = cumul_price;
        this.discount = discount;
        this.store_name = store_name;
        this.request = request;
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

    public String getRequest() {
        return request;
    }

    public boolean isProduct() {
        return product != null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(product, flags);
        out.writeInt(quantity);
        out.writeDouble(cumul_price);
        out.writeDouble(discount);
        out.writeString(store_name);
        out.writeString(request);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<OrderItem> CREATOR = new Parcelable.Creator<OrderItem>() {
        public OrderItem createFromParcel(Parcel in) {
            return new OrderItem(in);
        }

        public OrderItem[] newArray(int size) {
            return new OrderItem[size];
        }
    };

    private OrderItem(Parcel in) {
        product = in.readParcelable(Item.class.getClassLoader());
        quantity = in.readInt();
        cumul_price = in.readDouble();
        discount = in.readDouble();
        store_name = in.readString();
        request = in.readString();
    }
}
