package com.example.rup;

import android.content.Intent;
import android.os.Bundle;

import com.example.rup.adapters.LocationListAdapter;
import com.example.rup.databinding.ActivityMainBinding;
import com.example.rup.models.Location;
import com.example.rup.viewmodels.LocationListViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationOnClickListener{

    private LocationListViewModel locationListViewModel;
    private LiveData<String> customerName;
    LiveData<List<Location>> locationLiveData;
    LiveData<Event<String>> toastMessage;
    LiveData<Event<Boolean>> isReqSentEvent;

    LocationListAdapter locationListAdapter;

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        locationListViewModel  = ViewModelProviders.of(this).get(LocationListViewModel.class);

        RecyclerView rvLocaitons = activityMainBinding.rvLocaitons;
        rvLocaitons.setLayoutManager(new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false));
        locationListAdapter = new LocationListAdapter(locationListViewModel,this,this);
        rvLocaitons.setAdapter(locationListAdapter);

        locationLiveData = locationListViewModel.getmObservableLocationList();
        toastMessage = locationListViewModel.getToastMessage();
        isReqSentEvent = locationListViewModel.getIsReqSent();
        customerName = locationListViewModel.getmObservableCustomerName();
        locationLiveData.observe(this, new Observer<List<Location>>() {
            @Override
            public void onChanged(@Nullable List<Location> locationList) {
                Log.d("cnrr","changed" + locationList);
                if(locationList!=null)
                {
                    locationListAdapter.updateList(locationList);
                    if(locationList.size()>0)
                    {
                        setName();
                    }
                }
            }
        });

        toastMessage.observe(this, new Observer<Event<String>>() {
            @Override
            public void onChanged(@Nullable Event<String> event) {
                if(event!=null)
                {
                    Toast.makeText(MainActivity.this,event.getContentIfNotHandled(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        isReqSentEvent.observe(this, new Observer<Event<Boolean>>() {
            @Override
            public void onChanged(@Nullable Event<Boolean> isReqSent) {
                Log.d("cnrrr","reqsent " + isReqSent);
                Boolean req = isReqSent.getContentIfNotHandled();
                if(req==null)
                {
                    return;
                }

                //SHOW PROGRESS BAR HERE IF NEEDED WHILE THE REQ IS SENT

            }
        });

        customerName.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String customerName) {
                setName();
            }
        });



        if(locationLiveData.getValue()==null)
        {
            locationListViewModel.getLocationList("5c261ccb3000004f0067f6ec");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void setName()
    {
        activityMainBinding.setName(getResources().getString(R.string.testing));
        activityMainBinding.setLabel("Welcome to TravelMate");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openLocation(Location location) {
        Intent i = new Intent(this, LocationDetailActivity.class);
        i.putExtra("location_object", location);
        startActivity(i);
    }
}
