package com.ams303.cityconnect.data;

public class Category implements Comparable{
    private int id;
    private String name;
    private boolean food;

    public Category(int id, String name, boolean food) {
        this.id = id;
        this.name = name;
        this.food = food;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isFood() {
        return food;
    }

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof Category) || (food && ((Category) o).isFood())) return 0;
        if (food && !((Category) o).isFood()) return -1;
        else return 1;
    }
}
