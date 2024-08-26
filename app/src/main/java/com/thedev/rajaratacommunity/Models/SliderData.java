package com.thedev.rajaratacommunity.Models;


public class SliderData {

    // image url is used to
    // store the url of image
    private String imgUrl;
    private String title;
    private long id;

    // Constructor method.


    public SliderData(String imgUrl, String title, long id) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.id = id;
    }

    // Getter method
    public String getImgUrl() {
        return imgUrl;
    }

    // Setter method
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
