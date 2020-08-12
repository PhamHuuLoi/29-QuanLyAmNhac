package com.phananh.quanlishow.model;

import java.io.Serializable;

public class ThongKeCS implements Serializable {
    private String tenCS, tenBH;

    public ThongKeCS() {
    }

    public ThongKeCS(String tenCS, String tenBH) {
        this.tenCS = tenCS;
        this.tenBH = tenBH;
    }

    public String getTenCS() {
        return tenCS;
    }

    public void setTenCS(String tenCS) {
        this.tenCS = tenCS;
    }

    public String getTenBH() {
        return tenBH;
    }

    public void setTenBH(String tenBH) {
        this.tenBH = tenBH;
    }
}
