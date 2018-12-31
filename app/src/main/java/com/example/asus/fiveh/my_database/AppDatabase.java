package com.example.asus.fiveh.my_database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.asus.fiveh.models.Flower;

@Database(entities = {Flower.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "sadasd";
    private static volatile AppDatabase INSTANCE;

    public abstract FlowerDao flowerDao();

    // ________________________ (( )) ________________________

    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}


// __________________________ (( create one)) __________________________
// TODO: use singleton.
//AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-name").build();