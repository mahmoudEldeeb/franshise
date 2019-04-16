package com.franshise.franshise.models.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "fav_table")
public class FavModel  {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    private int user_id;
    private String franchiseName;
    private String franchiseImage;

    public FavModel(int id, String franchiseName, String franchiseImage) {
        this.id = id;
        this.franchiseName = franchiseName;
        this.franchiseImage = franchiseImage;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFranchiseName() {
        return franchiseName;
    }

    public String getFranchiseImage() {
        return franchiseImage;
    }

    public void setFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;
    }

    public void setFranchiseImage(String franchiseImage) {
        this.franchiseImage = franchiseImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
