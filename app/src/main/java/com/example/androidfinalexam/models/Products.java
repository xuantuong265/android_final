package com.example.androidfinalexam.models;

import org.json.JSONObject;

import java.io.Serializable;

public class Products implements Serializable {

    private String name, products_desc, image;
    private int id_b, amounts, id, id_categories;
    private double price, star;

    public Products(JSONObject jsonObject) {
    }


    public Products(int id, int id_brand, int id_categories, String name, String image, int amounts, double price, String products_desc, double star ) {
        this.id = id;
        this.id_b = id_brand;
        this.id_categories = id_categories;
        this.name = name;
        this.image = image;
        this.amounts = amounts;
        this.price = price;
        this.products_desc = products_desc;
        this.star = star;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducts_desc() {
        return products_desc;
    }

    public void setProducts_desc(String products_desc) {
        this.products_desc = products_desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId_b() {
        return id_b;
    }

    public void setId_b(int id_b) {
        this.id_b = id_b;
    }

    public int getAmounts() {
        return amounts;
    }

    public void setAmounts(int amounts) {
        this.amounts = amounts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public int getId_categories() {
        return id_categories;
    }

    public void setId_categories(int id_categories) {
        this.id_categories = id_categories;
    }
}
