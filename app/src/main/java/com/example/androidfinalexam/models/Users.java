package com.example.androidfinalexam.models;

import java.io.Serializable;

public class Users implements Serializable {

    private String email, password;

    public Users() {

    }

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
