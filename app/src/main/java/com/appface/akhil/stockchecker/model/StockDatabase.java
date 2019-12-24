package com.appface.akhil.stockchecker.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Stock.class}, version = 1)
public abstract class StockDatabase extends RoomDatabase {

    private static StockDatabase instance;
    public abstract StockDao stockdao();

    public static synchronized StockDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), StockDatabase.class, "Stock_database")
                        .fallbackToDestructiveMigration()
                        .build();
        }
        return instance;
    }
}
