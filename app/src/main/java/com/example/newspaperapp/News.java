package com.example.newspaperapp;

import android.graphics.Bitmap;

public class News {
    private String title, description, bitmapLink, releaseTime;

    public News(String title, String description, String bitmapLink, String releaseTime) {
        this.title = title;
        this.description = description;
        this.bitmapLink = bitmapLink;
        this.releaseTime = releaseTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBitmapLink() {
        return bitmapLink;
    }

    public void setBitmapLink(String bitmapLink) {
        this.bitmapLink = bitmapLink;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }
}
