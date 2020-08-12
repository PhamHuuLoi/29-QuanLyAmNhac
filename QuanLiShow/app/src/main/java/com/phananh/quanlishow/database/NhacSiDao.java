package com.phananh.quanlishow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.phananh.quanlishow.model.NhacSi;

import java.util.ArrayList;

public class NhacSiDao {

    Database dtb;
    SQLiteDatabase db;


    public NhacSiDao(Context context) {
        dtb = new Database(context);
        db = dtb.getWritableDatabase();
    }

    public ArrayList<NhacSi> getAll() {
        ArrayList<NhacSi> list = new ArrayList<>();
        Cursor cs = this.db.rawQuery("SELECT * FROM nhacsi", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int ma = cs.getInt(0);
            String ten = cs.getString(1);
            String hinh = cs.getString(2);
            NhacSi nhacSi = new NhacSi(ma, ten, hinh);
            list.add(nhacSi);
            cs.moveToNext();
        }
        cs.close();
        return list;
    }

    public boolean them(NhacSi nhacSi) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenNS", nhacSi.getTenNS());
        contentValues.put("imgNS", nhacSi.getImgNS());

        long r = db.insert("nhacsi", null, contentValues);
        if (r <= 0) {
            return false;
        }
        return true;
    }

    //Sửa
    public boolean sua(NhacSi nhacSi) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenNS", nhacSi.getTenNS());
        contentValues.put("imgNS", nhacSi.getImgNS());
        int r = db.update("nhacsi", contentValues, "maNS=?", new String[]{String.valueOf(nhacSi.getMaNS())});
        if (r <= 0) {
            return false;
        }
        return true;
    }

    public boolean xoa(NhacSi nhacSi) {
        int r = db.delete("nhacsi", "maNS=?", new String[]{String.valueOf(nhacSi.getMaNS())});
        db.delete("baihat", "maNS=?", new String[]{String.valueOf(nhacSi.getMaNS())});

        if (r <= 0) {
            return false;
        }
        return true;
    }
}
