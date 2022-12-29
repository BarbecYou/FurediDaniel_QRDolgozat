package hu.home.qrdolgozat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "qr.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table scanned_codes(id integer primary key autoincrement, content text not null, ip_address text, timestamp text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists scanned_codes");
    }

    public Boolean insertData(String content, String ip, String timestamp){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("content", content);
        cv.put("ip_address", ip);
        cv.put("timestamp", timestamp);
        long result = db.insert("scanned_codes", null, cv);
        if (result == -1) return false; else return true;
    }
}
