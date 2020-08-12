package com.phananh.quanlishow.model;

import java.io.Serializable;

public class CaSi implements Serializable {
    private int maCS;
    private String  hoTenCS, imgCS;


    public CaSi() {
    }

    public CaSi(int maCS, String hoTenCS, String imgCS) {
        this.maCS = maCS;
        this.hoTenCS = hoTenCS;
        this.imgCS = imgCS;
    }

    public int getMaCS() {
        return maCS;
    }

    public void setMaCS(int maCS) {
        this.maCS = maCS;
    }

    public String getHoTenCS() {
        return hoTenCS;
    }

    public void setHoTenCS(String hoTenCS) {
        this.hoTenCS = hoTenCS;
    }

    public String getImgCS() {
        return imgCS;
    }

    public void setImgCS(String imgCS) {
        this.imgCS = imgCS;
    }

    @Override
    public String toString() {
        return maCS+" | Ca sĩ "+hoTenCS;
    }
}
