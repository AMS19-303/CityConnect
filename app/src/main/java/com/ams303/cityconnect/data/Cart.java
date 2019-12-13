package com.ams303.cityconnect.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Cart {
    private List<CartItem> items;
    private String deliveryDate;
    private String address;
    private String comment;
    private double totalPrice;

    public Cart(List<CartItem> items, String deliveryDate, String address, String comment, double totalPrice) {
        this.items = items;
        this.deliveryDate = deliveryDate;
        this.address = address;
        this.comment = comment;
        this.totalPrice = totalPrice;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getAddress() {
        return address;
    }

    public String getComment() {
        return comment;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getSubtotal() {
        double res = 0;
        for(CartItem item : items){
            if (item instanceof Product){
                res += ((Product) item).getPrice();
            }
        }
        return res;
    }

    public static Cart getCart(Context context) {
        SharedPreferences sp = context.getSharedPreferences("CityConnect", MODE_PRIVATE);
        String result = sp.getString("cart", null);

        if (result == null) return new Cart(new ArrayList<CartItem>(), null, "", "", 0);

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
        return gson.fromJson(result, Cart.class);
    }

    public void saveCart(Context context){
        SharedPreferences sp = context.getSharedPreferences("CityConnect", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactory).create();
        edit.putString("cart", gson.toJson(this));
        edit.apply();
    }

    public static void resetCart(Context context) {
        new Cart(new ArrayList<CartItem>(), null, "", "", 0).saveCart(context);
    }

    public static void addItem(Context context, CartItem item) {
        Cart cart = Cart.getCart(context);
        cart.getItems().add(item);
        cart.saveCart(context);
    }

    public static void removeItem(Context context, int item) {
        Cart cart = Cart.getCart(context);
        cart.getItems().remove(item);
        cart.saveCart(context);
    }

    public int getSize() {
        return items.size();
    }

    public JSONObject export(){
        try {
            return new JSONObject(new Gson().toJson(this));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static RuntimeTypeAdapterFactory<CartItem> runtimeTypeAdapterFactory = RuntimeTypeAdapterFactory
            .of(CartItem.class, "type")
            .registerSubtype(Product.class, "Product")
            .registerSubtype(Request.class, "Request");
}
