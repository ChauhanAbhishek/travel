
package com.example.rup.models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "location_table")
public class Location implements Serializable {


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "place")
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("description")
    @Expose
    private String description;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {

        return place + " " + url + " " + date + " " + rate;
    }


}
