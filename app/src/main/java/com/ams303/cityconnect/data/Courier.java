package com.ams303.cityconnect.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.ams303.cityconnect.data.model.User;

public class Courier implements Parcelable {
    private User user;
    private double avg_rating;
    private int nr_deliveries;

    public Courier(User user, double avg_rating, int nr_deliveries) {
        this.user = user;
        this.avg_rating = avg_rating;
        this.nr_deliveries = nr_deliveries;
    }

    public User getUser() {
        return user;
    }

    public double getAvg_rating() {
        return avg_rating;
    }

    public int getNr_deliveries() {
        return nr_deliveries;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(user, flags);
        out.writeDouble(avg_rating);
        out.writeInt(nr_deliveries);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Courier> CREATOR = new Parcelable.Creator<Courier>() {
        public Courier createFromParcel(Parcel in) {
            return new Courier(in);
        }

        public Courier[] newArray(int size) {
            return new Courier[size];
        }
    };

    private Courier(Parcel in) {
        user = in.readParcelable(User.class.getClassLoader());
        avg_rating = in.readDouble();
        nr_deliveries = in.readInt();
    }
}
