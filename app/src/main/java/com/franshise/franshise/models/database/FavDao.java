package com.franshise.franshise.models.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.franshise.franshise.models.SharedPrefrenceModel;

import java.util.List;

@Dao
public interface FavDao {
    @Insert
    void insert(FavModel favModel);

    @Query("DELETE FROM fav_table")
    void deleteAll();

    @Query("SELECT * from fav_table WHERE user_id = :id")
    LiveData<List<FavModel>> getAllFav(int id);

}
