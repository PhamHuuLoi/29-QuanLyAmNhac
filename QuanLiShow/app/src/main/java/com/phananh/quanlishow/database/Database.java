package com.phananh.quanlishow.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    public Database(Context context) {
        super(context, "QUAN_LY_SHOW", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE camera(id integer PRIMARY KEY AUTOINCREMENT, image text)");

        db.execSQL("CREATE TABLE casi(" +
                "maCS integer PRIMARY KEY AUTOINCREMENT," +
                "hoTenCS text," +
                "imgCS text)");

        db.execSQL("CREATE TABLE nhacsi(" +
                "maNS integer PRIMARY KEY AUTOINCREMENT," +
                "tenNS text," +
                "imgNS text)");

        db.execSQL("CREATE TABLE baihat(" +
                "maBH integer PRIMARY KEY AUTOINCREMENT," +
                "tenBH text, " +
                "namSangTac text," +
                "maNS integer REFERENCES nhacsi(maNS))");

        db.execSQL("CREATE TABLE show(" +
                "maBD integer PRIMARY KEY AUTOINCREMENT, " +
                "maCS integer REFERENCES casi(maCS), " +
                "maBH integer REFERENCES baihat(maBH)," +
                "ngayBD text," +
                "noiBD text)");
    }

    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }
}
