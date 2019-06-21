package com.example.rup.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.rup.dao.LocationDao;
import com.example.rup.models.Location;

@Database(entities = {Location.class}, version = 1)
public abstract class LocationRoomDatabase extends RoomDatabase {
    private static volatile LocationRoomDatabase INSTANCE;
    public abstract LocationDao locationDao();

    public static LocationRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LocationRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocationRoomDatabase.class, "location_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}