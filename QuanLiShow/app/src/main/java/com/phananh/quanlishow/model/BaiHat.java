package com.phananh.quanlishow.model;

import java.io.Serializable;

public class BaiHat implements Serializable {
    private int maBH;
    private String tenBH, namSangTac, maNS;

    public BaiHat() {
    }

    public BaiHat(int maBH, String tenBH, String namSangTac, String maNS) {
        this.maBH = maBH;
        this.tenBH = tenBH;
        this.namSangTac = namSangTac;
        this.maNS = maNS;
    }

    public int getMaBH() {
        return maBH;
    }

    public void setMaBH(int maBH) {
        this.maBH = maBH;
    }

    public String getTenBH() {
        return tenBH;
    }

    public void setTenBH(String tenBH) {
        this.tenBH = tenBH;
    }

    public String getNamSangTac() {
        return namSangTac;
    }

    public void setNamSangTac(String namSangTac) {
        this.namSangTac = namSangTac;
    }

    public String getMaNS() {
        return maNS;
    }

    public void setMaNS(String maNS) {
        this.maNS = maNS;
    }

    @Override
    public String toString() {
        return maBH+" | "+tenBH;
    }
}
