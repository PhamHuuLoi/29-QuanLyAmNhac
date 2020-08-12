package com.phananh.quanlishow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.phananh.quanlishow.model.CaSi;

import java.util.ArrayList;

public class CaSiDao {

    Database dtb;
    SQLiteDatabase db;


    public CaSiDao(Context context) {
        dtb = new Database(context);
        db = dtb.getWritableDatabase();
    }

    public ArrayList<CaSi> getAll() {
        ArrayList<CaSi> list = new ArrayList<>();
        Cursor cs = this.db.rawQuery("SELECT * FROM casi", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int ma = cs.getInt(0);
            String ten = cs.getString(1);
            String hinh = cs.getString(2);
            CaSi caSi = new CaSi(ma, ten, hinh);
            list.add(caSi);
            cs.moveToNext();
        }
        cs.close();
        return list;
    }

    public boolean them(CaSi caSi) {
        ContentValues contentValues = new ContentValues();
//        contentValues.put("maCS", caSi.getMaCS());
        contentValues.put("hoTenCS", caSi.getHoTenCS());
        contentValues.put("imgCS", caSi.getImgCS());

        long r = db.insert("casi", null, contentValues);
        if (r <= 0) {
            return false;
        }
        return true;
    }

    //Sửa
    public boolean sua(CaSi caSi) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTenCS", caSi.getHoTenCS());
        contentValues.put("imgCS", caSi.getImgCS());
        int r = db.update("casi", contentValues, "maCS=?", new String[]{String.valueOf(caSi.getMaCS())});
        if (r <= 0) {
            return false;
        }
        return true;
    }

    public boolean xoa(CaSi caSi) {
        int r = db.delete("casi", "maCS=?", new String[]{String.valueOf(caSi.getMaCS())});
       db.delete("show", "maCS=?", new String[]{String.valueOf(caSi.getMaCS())});

        if (r <= 0) {
            return false;
        }
        return true;
    }
}
