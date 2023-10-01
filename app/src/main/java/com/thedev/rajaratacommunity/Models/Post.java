package com.thedev.rajaratacommunity.Models;

public class Post {
    String id,title,url,desc,auther;

    public Post() {
    }

    public Post(String id, String title, String url, String desc, String auther) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.desc = desc;
        this.auther = auther;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }
}
