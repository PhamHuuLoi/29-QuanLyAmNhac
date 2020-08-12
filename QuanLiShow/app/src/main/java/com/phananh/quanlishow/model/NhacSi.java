package com.phananh.quanlishow.model;

import java.io.Serializable;

public class NhacSi implements Serializable {
    private int maNS;
    private String  tenNS, imgNS;

    public NhacSi() {
    }

    public NhacSi(int maNS, String tenNS, String imgNS) {
        this.maNS = maNS;
        this.tenNS = tenNS;
        this.imgNS = imgNS;
    }

    public int getMaNS() {
        return maNS;
    }

    public void setMaNS(int maNS) {
        this.maNS = maNS;
    }

    public String getTenNS() {
        return tenNS;
    }

    public void setTenNS(String tenNS) {
        this.tenNS = tenNS;
    }

    public String getImgNS() {
        return imgNS;
    }

    public void setImgNS(String imgNS) {
        this.imgNS = imgNS;
    }

    @Override
    public String toString() {
        return  maNS+"| Nhạc sĩ "+tenNS;
    }
}
