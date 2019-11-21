package com.ams303.cityconnect.data.model;

public class User {
    private String email;
    private String first_name;
    private String last_name;
    private int nif;

    public User(String email, String first_name, String last_name, int nif) {
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.nif = nif;
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

    public int getNif() {
        return nif;
    }
}
