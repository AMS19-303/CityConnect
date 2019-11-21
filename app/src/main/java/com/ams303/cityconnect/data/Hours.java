package com.ams303.cityconnect.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Hours implements Parcelable {
    private String open;
    private String close;
    private int day_of_week;

    public Hours(String open, String close, int day_of_week) {
        this.open = open;
        this.close = close;
        this.day_of_week = day_of_week;
    }

    public String getOpen() {
        return open;
    }

    public String getClose() {
        return close;
    }

    public int getDay_of_week() {
        return day_of_week;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(open);
        out.writeString(close);
        out.writeInt(day_of_week);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Hours> CREATOR = new Parcelable.Creator<Hours>() {
        public Hours createFromParcel(Parcel in) {
            return new Hours(in);
        }

        public Hours[] newArray(int size) {
            return new Hours[size];
        }
    };

    // constructor that takes a Parcel and gives you an object populated with it's values
    private Hours(Parcel in) {
        open = in.readString();
        close = in.readString();
        day_of_week = in.readInt();
    }
}
