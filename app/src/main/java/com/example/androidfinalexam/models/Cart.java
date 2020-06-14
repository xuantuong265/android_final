package com.example.androidfinalexam.models;

public class Cart {

    private int id;
    private String namePro;
    private String imgPro;
    private double price;
    private int amount;

    public Cart(int id, String namePro, String imgPro, double price, int amount) {
        this.id = id;
        this.namePro = namePro;
        this.imgPro = imgPro;
        this.price = price;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamePro() {
        return namePro;
    }

    public void setNamePro(String namePro) {
        this.namePro = namePro;
    }

    public String getImgPro() {
        return imgPro;
    }

    public void setImgPro(String imgPro) {
        this.imgPro = imgPro;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
