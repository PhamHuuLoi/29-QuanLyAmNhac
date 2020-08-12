package com.phananh.quanlishow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.phananh.quanlishow.model.ThongTinShow;

import java.util.ArrayList;

public class ShowDao {

    Database dtb;
    SQLiteDatabase db;


    public ShowDao(Context context) {
        dtb = new Database(context);
        db = dtb.getWritableDatabase();
    }

    public ArrayList<ThongTinShow> getAll() {
        ArrayList<ThongTinShow> list = new ArrayList<>();
        Cursor cs = this.db.rawQuery("SELECT * FROM show", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int ma = cs.getInt(0);
            int macs = cs.getInt(1);
            int mabh = cs.getInt(2);
            String ngay = cs.getString(3);
            String noi = cs.getString(4);

            ThongTinShow show = new ThongTinShow(ma, macs, mabh, ngay, noi);
            list.add(show);
            cs.moveToNext();
        }
        cs.close();
        return list;
    }

    public boolean them(ThongTinShow show) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maCS", show.getMaCS());
        contentValues.put("maBH", show.getMaBH());
        contentValues.put("ngayBD", show.getNgayBD());
        contentValues.put("noiBD", show.getNoiBD());


        long r = db.insert("show", null, contentValues);
        if (r <= 0) {
            return false;
        }
        return true;
    }

    //Sửa
    public boolean sua(ThongTinShow show) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("maCS", show.getMaCS());
        contentValues.put("maBH", show.getMaBH());
        contentValues.put("ngayBD", show.getNgayBD());
        contentValues.put("noiBD", show.getNoiBD());
        int r = db.update("show", contentValues, "maBD=?", new String[]{String.valueOf(show.getMaBD())});
        if (r <= 0) {
            return false;
        }
        return true;
    }

    public boolean xoa(ThongTinShow show) {
        int r = db.delete("show", "maBD=?", new String[]{String.valueOf(show.getMaBD())});
        if (r <= 0) {
            return false;
        }
        return true;
    }
}
