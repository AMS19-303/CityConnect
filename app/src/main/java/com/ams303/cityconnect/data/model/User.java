package com.ams303.cityconnect.data.model;

public class User {
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
}
