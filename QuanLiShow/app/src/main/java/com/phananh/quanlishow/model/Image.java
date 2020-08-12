package com.phananh.quanlishow.model;

import java.io.Serializable;

public class Image implements Serializable {

    private int id;
    private String image;

    public Image() {
    }

    public Image(int id, String image2) {
        this.id = id;
        this.image = image2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image2) {
        this.image = image2;
    }
}
