package com.example.rup;

import android.content.Intent;
import android.os.Bundle;

import com.example.rup.databinding.ActivityLocationDetailBinding;
import com.example.rup.models.Location;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.View;

public class LocationDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityLocationDetailBinding activityLocationDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_location_detail);

        Intent i = getIntent();

        //CHANGE TO PARCEABLE BEFORE SENDING
        Location location = (Location)i.getSerializableExtra("location_object");


        activityLocationDetailBinding.setLocation(location);

        activityLocationDetailBinding.backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

}

