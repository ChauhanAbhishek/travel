package com.example.rup.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.rup.models.Location;

import java.util.List;

@Dao
public interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Location location);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Location> locations);

    @Query("DELETE FROM location_table")
    void deleteAll();

    @Query("SELECT * from location_table ")
    LiveData<List<Location>> getAllLocations();
}
