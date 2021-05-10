package com.example.supremenews.models;

import android.os.Parcelable;

import java.io.Serializable;


public class News implements Serializable {
    private String _id;
    private String title;
    private String subtitle;
    private String published_at;
    private String likes;
    private String body;
    private String image;
    private String category;
    private String __v;

    public News(String _id, String title, String subtitle, String published_at, String likes, String body, String image, String category,String __v) {
        this._id = _id;
        this.title = title;
        this.subtitle = subtitle;
        this.published_at = published_at;
        this.likes = likes;
        this.body = body;
        this.image = image;
        this.category = category;
        this.__v = __v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String get__v() {
        return __v;
    }

    public void set__v(String __v) {
        this.__v = __v;
    }
}
