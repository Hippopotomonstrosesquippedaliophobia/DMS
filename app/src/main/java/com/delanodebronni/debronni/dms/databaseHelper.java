package com.delanodebronni.debronni.dms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Debronni on 02-May-17.
 */
public class databaseHelper extends SQLiteOpenHelper {
    public static final Integer VERSION_NUM = 1;
    public static final String DATABASE_NAME = "contacts.db";
    public static final String TABLE_NAME = "contact_table";
    public static final String COL_ID = "ID";
    public static final String COL_NAME = "NAME";
    public static final String COL_EMAIL = "EMAIL";
    public static final String COL_HNUM = "HNUM";
    public static final String COL_CNUM = "CNUM";
    public static final String COL_TYPE = "TYPE";
    public static final String COL_FAV = "FAV";

    public databaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,EMAIL TEXT,HNUM TEXT,CNUM TEXT,TYPE TEXT,FAV TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData (String name,String email,String hnum,String cnum,String type,String fav){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_HNUM, hnum);
        contentValues.put(COL_CNUM, cnum);
        contentValues.put(COL_TYPE, type);
        contentValues.put(COL_FAV, "false");
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public Integer deleteData () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME= '"+TABLE_NAME+"'");
        return db.delete(TABLE_NAME,null,null);
    }

    public Cursor getContact(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int idint = Integer.parseInt(id);
        idint = idint + 1;
        id = Integer.toString(idint);
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where TRIM(ID) = '"+id.trim()+"'", null);
        if(res.moveToFirst()){
            return res;
        }else {
            return res;
        }
    }
    public int getBusiness(){
        SQLiteDatabase db = this.getWritableDatabase();
        String business = "Business";
        Cursor res = db.rawQuery("SELECT * FROM '"+TABLE_NAME+"' WHERE TYPE = '"+business+"'",null);
        int b  = res.getCount();
        return b;
    }
    public int getPersonal(){
        SQLiteDatabase db = this.getWritableDatabase();
        String personal = "Personal";
        Cursor res = db.rawQuery("SELECT * FROM '"+TABLE_NAME+"' WHERE TYPE = '"+personal+"'",null);
        int p = res.getCount();
        return p;
    }

    public Cursor getFavContact(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int idint = Integer.parseInt(id);
        idint = idint - 1 ;
        id = Integer.toString(idint);
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" TRIM(id) = '"+id+"'", null);
        if(res.moveToFirst()){
            return res;
        }else {
            return res;
        }
    }
    public String getContactID(String name,String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where TRIM(NAME) = '"+name.trim()+"' AND TRIM(EMAIL) = '"+email.trim()+"'", null);
        if(res.moveToFirst()){
            String id = res.getString(0);
            return id;
        }else {
            return null;
        }
    }

    public List<String> search(String name){
        List<String> contacts = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Select All Query
        String selectQuery = "SELECT  * FROM '" + TABLE_NAME+"' WHERE NAME = '"+name+"'" ;

        Cursor res = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (res.moveToFirst()) {
            do {
                if(res.getString(6).equals("false")) {
                    contacts.add("☆ "+res.getString(1) + "\n\n Home #: "+res.getString(3) +"\n Cell #: "+res.getString(4) +"\n");
                }if(res.getString(6).equals("true")) {
                    contacts.add("★ "+res.getString(1) + "\n\n Home #: "+res.getString(3) +"\n Cell #: "+res.getString(4) +"\n");
                }
            } while (res.moveToNext());
        }

        // closing connection
        res.close();
        db.close();

        // returning labels
        return contacts;
    }

    public int updateData(String idtemp,String NAME,String EMAIL,String HNUM,String CNUM,String TYPE,String fav) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows;
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where TRIM(ID) = '"+idtemp.trim()+"'", null);
        if(res.moveToFirst()){
            if(res.getString(6).equals("true")){
                ContentValues contentValues = new ContentValues();
                contentValues.put(COL_FAV, "false");
                String[] whereArgs = {idtemp};
                rows = db.update(TABLE_NAME, contentValues, "ID = ?", whereArgs);
                return -1;
            }
            else{
                ContentValues contentValues = new ContentValues();
                contentValues.put(COL_FAV, "true");
                String[] whereArgs = {idtemp};
                rows = db.update(TABLE_NAME, contentValues, "ID = ?", whereArgs);
                return 1;
            }
        }else{

        }
        return 0;
    }
    public List<String> getAllFavs(){
        List<String> contacts = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Select All Query
        String truth = "true";
        String selectQuery = "SELECT  * FROM '" + TABLE_NAME+"' WHERE FAV = '"+truth+"'" ;

        Cursor res = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (res.moveToFirst()) {
            do {
                if(res.getString(6).equals("false")) {
                    contacts.add("☆ "+res.getString(1) + " (" + res.getString(5) + ") \n Home: "+res.getString(3)+"\n  Cell: " + res.getString(4) + "\n");
                }if(res.getString(6).equals("true")) {
                    contacts.add("★ "+res.getString(1) + " (" + res.getString(5) + ") \n Home: "+res.getString(3)+"\n  Cell: " + res.getString(4) + "\n");
                }
            } while (res.moveToNext());
        }
        // returning labels
        return contacts;

    }

    public List<String> getAllLabels(){
        List<String> contacts = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME ;

        Cursor res = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (res.moveToFirst()) {
            do {
                if(res.getString(6).equals("false")) {
                    contacts.add("☆ "+res.getString(1) + " (" + res.getString(5) + ") \n    Cell: " + res.getString(4) + "\n");
                }if(res.getString(6).equals("true")) {
                    contacts.add("★ "+res.getString(1) + " (" + res.getString(5) + ")  \n    Cell: " + res.getString(4) + "\n");
                }
            } while (res.moveToNext());
        }

        // closing connection
        res.close();
        db.close();

        // returning labels
        return contacts;
    }
}
