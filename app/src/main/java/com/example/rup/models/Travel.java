
package com.example.rup.models;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Travel {

    @SerializedName("cust_name")
    @Expose
    private String custName;
    @SerializedName("locations")
    @Expose
    private List<Location> locations = null;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public String toString() {

        return custName + " " + locations;
    }
}
