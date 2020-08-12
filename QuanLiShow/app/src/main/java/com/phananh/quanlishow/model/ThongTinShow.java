package com.phananh.quanlishow.model;

import java.io.Serializable;

public class ThongTinShow implements Serializable {
private int maBD;
private int maCS;
private int  maBH;
private String ngayBD, noiBD;


    public ThongTinShow() {
    }

    public ThongTinShow(int maBD, int maCS, int maBH, String ngayBD, String noiBD) {
        this.maBD = maBD;
        this.maCS = maCS;
        this.maBH = maBH;
        this.ngayBD = ngayBD;
        this.noiBD = noiBD;
    }

    public int getMaBD() {
        return maBD;
    }

    public void setMaBD(int maBD) {
        this.maBD = maBD;
    }

    public int getMaCS() {
        return maCS;
    }

    public void setMaCS(int maCS) {
        this.maCS = maCS;
    }

    public int getMaBH() {
        return maBH;
    }

    public void setMaBH(int maBH) {
        this.maBH = maBH;
    }

    public String getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(String ngayBD) {
        this.ngayBD = ngayBD;
    }

    public String getNoiBD() {
        return noiBD;
    }

    public void setNoiBD(String noiBD) {
        this.noiBD = noiBD;
    }
}
