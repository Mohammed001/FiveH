package com.example.asus.fiveh.my_database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.asus.fiveh.my_database.TaskContract.AdTable;

public class TaskDbHelper extends SQLiteOpenHelper {

    // The name of the database
    private static final String DATABASE_NAME = "adsDb.db";

    // If you change the database schema, you must increment the database version
    private static final int VERSION = 1;


    // Constructor
    TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    /**
     * Called when the tasks database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tasks table (careful to follow SQL formatting rules)
        final String CREATE_TABLE = "CREATE TABLE " + AdTable.TABLE_NAME + " (" +
                AdTable._ID + " INTEGER PRIMARY KEY, " +
                AdTable.COLUMN_AD_TYPE + " TEXT NOT NULL, " +
                AdTable.COLUMN_AD_TEXT + " TEXT NOT NULL, " +
                AdTable.COLUMN_FILE_PATH + " INTEGER NOT NULL, " +
                AdTable.COLUMN_AD_ID + " INTEGER NOT NULL, " +
                AdTable.COLUMN_AD_PAGE + " INTEGER NOT NULL" +
                ");";

        db.execSQL(CREATE_TABLE);
    }


    /**
     * This method discards the old table of data and calls onCreate to recreate a new one.
     * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AdTable.TABLE_NAME);
        onCreate(db);
    }
}
