package com.example.androidfinalexam.models;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

public class Orders implements Serializable {

    private int id, id_user;
    private String name, address, phone;
    private double total;
    private int status;
    private String date;

    public Orders(int id, int id_user, String name, String address, String phone, double total, int status, String date) {
        this.id = id;
        this.id_user = id_user;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.total = total;
        this.status = status;
        this.date = date;
    }

    public Orders(JSONObject object) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
