package com.ams303.cityconnect.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private int id;
    private String name;
    private String description;
    private int base_unit;
    private String unit;
    private double unit_price;
    private int store_id;

    public Item(int id, String name, String description, int base_unit, String unit, double price, int store_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.base_unit = base_unit;
        this.unit = unit;
        this.unit_price = price;
        this.store_id = store_id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getBase_unit() {
        return base_unit;
    }

    public String getUnit() {
        return unit;
    }

    public double getPrice() {
        return unit_price;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public int getStore_id() {
        return store_id;
    }

    public String getStrBase_unit() {
        if(base_unit == 1) {
            return "";
        }
        return base_unit + " ";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeString(description);
        out.writeInt(base_unit);
        out.writeString(unit);
        out.writeDouble(unit_price);
        out.writeInt(store_id);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    private Item(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        base_unit = in.readInt();
        unit = in.readString();
        unit_price = in.readDouble();
        store_id = in.readInt();
    }
}
