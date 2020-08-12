package com.example.myapplication;

public class XeMay {
    int imgIcon;
    String maXe, tenXe, hangSX;

    public XeMay() {
    }

    public XeMay(int imgIcon, String maXe, String tenXe, String hangSX) {
        this.imgIcon = imgIcon;
        this.maXe = maXe;
        this.tenXe = tenXe;
        this.hangSX = hangSX;
    }

    public int getImgIcon() {
        return imgIcon;
    }

    public void setImgIcon(int imgIcon) {
        this.imgIcon = imgIcon;
    }

    public String getMaXe() {
        return maXe;
    }

    public void setMaXe(String maXe) {
        this.maXe = maXe;
    }

    public String getTenXe() {
        return tenXe;
    }

    public void setTenXe(String tenXe) {
        this.tenXe = tenXe;
    }

    public String getHangSX() {
        return hangSX;
    }

    public void setHangSX(String hangSX) {
        this.hangSX = hangSX;
    }
}
