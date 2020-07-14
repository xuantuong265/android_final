package com.example.androidfinalexam.models;

public class Comment {
    private int id, id_user, id_products;
    private String content, image;
    private int star;
    private String date, email, name_products, image_products;

    public Comment(int id, int id_user, int id_products, String content, String image, int star, String date) {
        this.id = id;
        this.id_user = id_user;
        this.id_products = id_products;
        this.content = content;
        this.image = image;
        this.star = star;
        this.date = date;
    }

    public Comment(int id, int id_user, int id_products, String content, String image, int star, String date, String email) {
        this.id = id;
        this.id_user = id_user;
        this.id_products = id_products;
        this.content = content;
        this.image = image;
        this.star = star;
        this.date = date;
        this.email = email;
    }

    public Comment(int id, int id_user, int id_products, String content, String image, int star, String date, String name_products, String image_products) {
        this.id = id;
        this.id_user = id_user;
        this.id_products = id_products;
        this.content = content;
        this.image = image;
        this.star = star;
        this.date = date;
        this.name_products = name_products;
        this.image_products = image_products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_products() {
        return id_products;
    }

    public void setId_products(int id_products) {
        this.id_products = id_products;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName_products() {
        return name_products;
    }

    public void setName_products(String name_products) {
        this.name_products = name_products;
    }

    public String getImage_products() {
        return image_products;
    }

    public void setImage_products(String image_products) {
        this.image_products = image_products;
    }
}
