package com.swip.swipemenulistview;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class SwipeMenuItem {
    private Drawable background;
    private Drawable icon;

    /* renamed from: id */
    private int f44id;
    private Context mContext;
    private String title;
    private int titleColor;
    private int titleSize;
    private int width;

    public SwipeMenuItem(Context context) {
        this.mContext = context;
    }

    public int getId() {
        return this.f44id;
    }

    public void setId(int id) {
        this.f44id = id;
    }

    public int getTitleColor() {
        return this.titleColor;
    }

    public int getTitleSize() {
        return this.titleSize;
    }

    public void setTitleSize(int titleSize2) {
        this.titleSize = titleSize2;
    }

    public void setTitleColor(int titleColor2) {
        this.titleColor = titleColor2;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public void setTitle(int resId) {
        setTitle(this.mContext.getString(resId));
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public void setIcon(Drawable icon2) {
        this.icon = icon2;
    }

    public void setIcon(int resId) {
        this.icon = this.mContext.getResources().getDrawable(resId);
    }

    public Drawable getBackground() {
        return this.background;
    }

    public void setBackground(Drawable background2) {
        this.background = background2;
    }

    public void setBackground(int resId) {
        this.background = this.mContext.getResources().getDrawable(resId);
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width2) {
        this.width = width2;
    }
}
