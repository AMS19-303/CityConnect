package com.ams303.cityconnect.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.ams303.cityconnect.data.Courier;
import com.ams303.cityconnect.data.Order;
import com.ams303.cityconnect.data.OrderItem;

public class User implements Parcelable {
    private String email;
    private String first_name;
    private String last_name;
    private String address;
    private int nif;
    private int phone;

    public User(String email, String first_name, String last_name, String address, int nif, int phone ) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.nif = nif;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getAddress() {
        return address;
    }

    public int getNif() {
        return nif;
    }

    public int getPhone() {
        return phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(email);
        out.writeString(first_name);
        out.writeString(last_name);
        out.writeString(address);
        out.writeInt(nif);
        out.writeInt(phone);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    private User(Parcel in) {
        email = in.readString();
        first_name = in.readString();
        last_name = in.readString();
        address = in.readString();
        nif = in.readInt();
        phone = in.readInt();
    }
}
