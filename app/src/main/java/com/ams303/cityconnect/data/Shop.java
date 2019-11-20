package com.ams303.cityconnect.data;

public class Shop {
    private String name;
    private String type;
    private String image_url;

    public Shop(String name, String type, String image_url) {
        this.name = name;
        this.type = type;
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getImage_url() {
        return image_url;
    }
}
