package com.thedev.rajaratacommunity.Models;

public class Event {
    String desc,image,title;
    int id;

    public Event() {

    }

    public Event(String desc, String image, String title, int id) {
        this.desc = desc;
        this.image = image;
        this.title = title;
        this.id = id;

    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
