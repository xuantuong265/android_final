package com.example.androidfinalexam.models;

public class Notification {

    private int id;
    private String title, content, img, date;

    public Notification(int id, String title, String content, String img, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.img = img;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
