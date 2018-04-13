package com.example.sandy.attendencesystem;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;



import static android.widget.Toast.LENGTH_LONG;


public class DBHelper extends SQLiteOpenHelper {
private static final String TAG=DBHelper.class.getSimpleName();
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "EMPID";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER  PRIMARY KEY AUTOINCREMENT ,NAME TEXT,EMPID INTEGER UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String empid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       // contentValues.put(COL_1,id);
        Log.e(TAG, "e3");
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,empid);

        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1) {

            Log.e(TAG,"insert false data method");
            return false;
        }
        else
            Log.e(TAG,"insert  data method true");
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id, String name,String empid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,empid);


        db.update(TABLE_NAME, contentValues, "EMPID = ?",new String[] { empid });
        return true;
    }

    public Integer deleteData (String empid) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "EMPID = ?",new String[] { empid });



}

    public boolean isExist(String empno) {
        Log.e(TAG, "e4");
        SQLiteDatabase db = this.getReadableDatabase();
      Cursor  cur = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE EMPID = '" + empno + "'", null);
        boolean exist = (cur.getCount() > 0);
        boolean value = exist;
        Log.e(TAG,"something"+value);
        cur.close();

        return exist;

    }
}