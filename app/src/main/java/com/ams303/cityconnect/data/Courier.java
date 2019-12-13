package com.ams303.cityconnect.data;

import com.ams303.cityconnect.data.model.User;

public class Courier {
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
}
