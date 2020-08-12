package com.phananh.quanlishow.model;

import java.io.Serializable;

public class ThongKeNS implements Serializable {
    private String tenNhacSi, tenBH;

    public ThongKeNS() {
    }

    public ThongKeNS(String tenNhacSi, String tenBH) {
        this.tenNhacSi = tenNhacSi;
        this.tenBH = tenBH;
    }

    public String getTenNhacSi() {
        return tenNhacSi;
    }

    public void setTenNhacSi(String tenNhacSi) {
        this.tenNhacSi = tenNhacSi;
    }

    public String getTenBH() {
        return tenBH;
    }

    public void setTenBH(String tenBH) {
        this.tenBH = tenBH;
    }
}
