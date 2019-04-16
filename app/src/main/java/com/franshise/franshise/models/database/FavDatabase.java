package com.franshise.franshise.models.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {FavModel.class}, version = 2)
public abstract class  FavDatabase extends RoomDatabase {
    private static FavDatabase instance;

    public abstract FavDao favDao();

    public static synchronized FavDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavDatabase.class, "fav_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
