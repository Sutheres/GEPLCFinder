package com.example.sutheres.geplcfinder.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.sutheres.geplcfinder.data.PLCContract.PLCEntry;

/**
 * Created by Sutheres on 2/4/2017.
 */

public class PLCDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "ge_plcs.db";

    public PLCDbHelper(Context context) {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        // CREATE TABLE plcs (_id INTEGER PRIMARY KEY AUTOINCREMENT,
        //                      ip_Address TEXT NOT NULL,
        //                      organization TEXT NOT NULL,
        //                      isp TEXT NOT NULL,
        //                      lat TEXT NOT NULL,
        //                      lng TEXT NOT NULL,
        //                      city TEXT);
        String SQL_CREATE_PLCS_TABLE = "CREATE TABLE " + PLCEntry.TABLE_NAME + " ("
                + PLCEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PLCEntry.COLUMN_IP_ADDRESS + " TEXT NOT NULL, "
                + PLCEntry.COLUMN_ORGANIZATION + " TEXT NOT NULL, "
                + PLCEntry.COLUMN_ISP + " TEXT NOT NULL, "
                + PLCEntry.COLUMN_LATITUDE + " TEXT NOT NULL, "
                + PLCEntry.COLUMN_LONGITUDE + " TEXT NOT NULL, "
                + PLCEntry.COLUMN_CITY + " TEXT); ";

        db.execSQL(SQL_CREATE_PLCS_TABLE);

        ContentValues values = new ContentValues();
        values.put(PLCEntry.COLUMN_IP_ADDRESS, "206.19.236.60");
        values.put(PLCEntry.COLUMN_ORGANIZATION, "AT&T Enhanced Network Services");
        values.put(PLCEntry.COLUMN_ISP, "CERFnet");
        values.put(PLCEntry.COLUMN_LATITUDE, "29.5557");
        values.put(PLCEntry.COLUMN_LONGITUDE, "-95.6335");
        values.put(PLCEntry.COLUMN_CITY, "Sugar Land");
        db.insert(PLCEntry.TABLE_NAME, null, values);
        values.clear();

        values.put(PLCEntry.COLUMN_IP_ADDRESS, "50.252.247.34");
        values.put(PLCEntry.COLUMN_ORGANIZATION, "Comcast Business");
        values.put(PLCEntry.COLUMN_ISP, "Comcast Cable");
        values.put(PLCEntry.COLUMN_LATITUDE, "42.0166");
        values.put(PLCEntry.COLUMN_LONGITUDE, "-71.2231");
        values.put(PLCEntry.COLUMN_CITY, "Mansfield");
        db.insert(PLCEntry.TABLE_NAME, null, values);
        values.clear();

        values.put(PLCEntry.COLUMN_IP_ADDRESS, "96.57.176.74");
        values.put(PLCEntry.COLUMN_ORGANIZATION, "Optimum Online");
        values.put(PLCEntry.COLUMN_ISP, "Optimum Online");
        values.put(PLCEntry.COLUMN_LATITUDE, "40.0721");
        values.put(PLCEntry.COLUMN_LONGITUDE, "-74.2069");
        values.put(PLCEntry.COLUMN_CITY, "Lakewood");
        db.insert(PLCEntry.TABLE_NAME, null, values);
        values.clear();

        values.put(PLCEntry.COLUMN_IP_ADDRESS, "162.254.20.24");
        values.put(PLCEntry.COLUMN_ORGANIZATION, "Pioneer Cellular");
        values.put(PLCEntry.COLUMN_ISP, "Pioneer Cellular");
        values.put(PLCEntry.COLUMN_LATITUDE, "35.8510");
        values.put(PLCEntry.COLUMN_LONGITUDE, "-97.9515");
        values.put(PLCEntry.COLUMN_CITY, "Kingfisher");
        db.insert(PLCEntry.TABLE_NAME, null, values);
        values.clear();

        values.put(PLCEntry.COLUMN_IP_ADDRESS, "166.149.195.51");
        values.put(PLCEntry.COLUMN_ORGANIZATION, "Verizon Wireless");
        values.put(PLCEntry.COLUMN_ISP, "Verizon Wireless");
        values.put(PLCEntry.COLUMN_LATITUDE, "38.0000");
        values.put(PLCEntry.COLUMN_LONGITUDE, "-97.0000");
        db.insert(PLCEntry.TABLE_NAME, null, values);
        values.clear();

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
