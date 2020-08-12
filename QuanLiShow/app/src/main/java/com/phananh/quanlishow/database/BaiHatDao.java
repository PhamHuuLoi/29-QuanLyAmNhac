package com.phananh.quanlishow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.phananh.quanlishow.model.BaiHat;

import java.util.ArrayList;

public class BaiHatDao {

    Database dtb;
    SQLiteDatabase db;


    public BaiHatDao(Context context) {
        dtb = new Database(context);
        db = dtb.getWritableDatabase();
    }

    public ArrayList<BaiHat> getAll() {
        ArrayList<BaiHat> list = new ArrayList<>();
        Cursor cs = this.db.rawQuery("SELECT * FROM baihat", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int ma = cs.getInt(0);
            String ten = cs.getString(1);
            String nam = cs.getString(2);
            String maNS = cs.getString(3);
            BaiHat baiHat = new BaiHat(ma, ten, nam, maNS);
            list.add(baiHat);
            cs.moveToNext();
        }
        cs.close();
        return list;
    }

    public boolean them(BaiHat baiHat) {
        ContentValues contentValues = new ContentValues();
//        contentValues.put("maBH", baiHat.getMaBH());
        contentValues.put("tenBH", baiHat.getTenBH());
        contentValues.put("namSangTac", baiHat.getNamSangTac());
        contentValues.put("maNS", baiHat.getMaNS());

        long r = db.insert("baihat", null, contentValues);
        if (r <= 0) {
            return false;
        }
        return true;
    }

    //Sửa
    public boolean sua(BaiHat baiHat) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenBH", baiHat.getTenBH());
        contentValues.put("namSangTac", baiHat.getNamSangTac());
        contentValues.put("maNS", baiHat.getMaNS());
        int r = db.update("baihat", contentValues, "maBH=?", new String[]{String.valueOf(baiHat.getMaBH())});
        if (r <= 0) {
            return false;
        }
        return true;
    }

    public boolean xoa(BaiHat baiHat) {
        int r = db.delete("baihat", "maBH=?", new String[]{String.valueOf(baiHat.getMaBH())});
        db.delete("show", "maBH=?", new String[]{String.valueOf(baiHat.getMaBH())});

        if (r <= 0) {
            return false;
        }
        return true;
    }
}
