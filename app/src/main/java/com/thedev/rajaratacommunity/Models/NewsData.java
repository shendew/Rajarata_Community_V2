package com.thedev.rajaratacommunity.Models;

public class NewsData {

    String title,tdate,imglink,link;

    public NewsData(String title, String tdate, String imglink, String link) {
        this.title = title;
        this.tdate = tdate;
        this.imglink = imglink;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public String getImglink() {
        return imglink;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
