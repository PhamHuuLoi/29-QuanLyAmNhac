package com.phananh.quanlishow.model;

import java.io.Serializable;

public class ThongKe implements Serializable {
    private String ten1, ten2;

    public ThongKe() {
    }

    public ThongKe(String ten1, String ten2) {
        this.ten1 = ten1;
        this.ten2 = ten2;
    }

    public String getTen1() {
        return ten1;
    }

    public void setTen1(String ten1) {
        this.ten1 = ten1;
    }

    public String getTen2() {
        return ten2;
    }

    public void setTen2(String ten2) {
        this.ten2 = ten2;
    }
}
