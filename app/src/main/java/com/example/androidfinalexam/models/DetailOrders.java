package com.example.androidfinalexam.models;

import org.json.JSONObject;

public class DetailOrders {

    private int id, id_oders, id_products;
    private String name_products;
    private double price;
    private int amounts;

    public DetailOrders(int id, int id_oders, int id_products, String name_products, double price, int amounts) {
        this.id = id;
        this.id_oders = id_oders;
        this.id_products = id_products;
        this.name_products = name_products;
        this.price = price;
        this.amounts = amounts;
    }

    public DetailOrders(JSONObject object) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_oders() {
        return id_oders;
    }

    public void setId_oders(int id_oders) {
        this.id_oders = id_oders;
    }

    public int getId_products() {
        return id_products;
    }

    public void setId_products(int id_products) {
        this.id_products = id_products;
    }

    public String getName_products() {
        return name_products;
    }

    public void setName_products(String name_products) {
        this.name_products = name_products;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmounts() {
        return amounts;
    }

    public void setAmounts(int amounts) {
        this.amounts = amounts;
    }
}
