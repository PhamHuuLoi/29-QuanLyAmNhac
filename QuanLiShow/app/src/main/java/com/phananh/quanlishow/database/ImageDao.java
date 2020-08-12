package com.phananh.quanlishow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.phananh.quanlishow.model.Image;
import java.util.ArrayList;

public class ImageDao {

    /* renamed from: db */
    SQLiteDatabase f9db;
    Database dtb;

    public ImageDao(Context context) {
        Database database = new Database(context);
        this.dtb = database;
        this.f9db = database.getWritableDatabase();
    }

    public ArrayList<Image> getALl() {
        ArrayList<Image> list = new ArrayList<>();
        Cursor cs = this.f9db.rawQuery("SELECT * FROM camera", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            try {
                list.add(new Image(cs.getInt(0), cs.getString(1)));
                cs.moveToNext();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        cs.close();
        return list;
    }

    public boolean updateImage(Image image) {
        ContentValues values = new ContentValues();
        values.put("image", image.getImage());
        return this.f9db.update("camera", values, "id=?", new String[]{String.valueOf(image.getId())}) > 0;
    }

    public boolean xoa(int id) {
        return this.f9db.delete("camera", "id=?", new String[]{String.valueOf(id)}) > 0;
    }

    public boolean them(Image image) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", image.getImage());
        if (this.f9db.insert("camera", null, contentValues) <= 0) {
            return false;
        }
        return true;
    }
}
