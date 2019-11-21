package com.ams303.cityconnect.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Store implements Parcelable {
    private int id;
    private String name;
    private String type;
    private String photo;
    private double[] gps;
    private Hours[] business_hours;
    private String[] categories;

    public Store(int id, String name, String type, String photo, double[] gps, Hours[] business_hours, String[] categories) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.photo = photo;
        this.gps = gps;
        this.business_hours = business_hours;
        this.categories = categories;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPhoto() {
        return photo;
    }

    public double[] getGps() {
        return gps;
    }

    public Hours[] getBusiness_hours() {
        return business_hours;
    }

    public String[] getCategories() {
        return categories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeString(name);
        out.writeString(type);
        out.writeString(photo);
        out.writeDoubleArray(gps);
        out.writeTypedArray(business_hours, flags);
        out.writeStringArray(categories);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Store> CREATOR = new Parcelable.Creator<Store>() {
        public Store createFromParcel(Parcel in) {
            return new Store(in);
        }

        public Store[] newArray(int size) {
            return new Store[size];
        }
    };

    // constructor that takes a Parcel and gives you an object populated with it's values
    private Store(Parcel in) {
        id = in.readInt();
        name = in.readString();
        type = in.readString();
        photo = in.readString();
        gps = in.createDoubleArray();
        business_hours = in.createTypedArray(Hours.CREATOR);
        categories = in.createStringArray();
    }
}
