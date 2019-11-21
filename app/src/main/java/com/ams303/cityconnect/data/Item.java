package com.ams303.cityconnect.data;

public class Item {
    private String name;
    private String description;
    private int base_unit;
    private String unit;
    private double unit_price;

    public Item(String name, String description, int base_unit, String unit, double price) {
        this.name = name;
        this.description = description;
        this.base_unit = base_unit;
        this.unit = unit;
        this.unit_price = price;
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

    public String getStrBase_unit() {
        if(base_unit == 1) {
            return "";
        }
        return base_unit + " ";
    }
}
