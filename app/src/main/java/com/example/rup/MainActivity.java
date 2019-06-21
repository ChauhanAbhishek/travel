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
                }
            }
        });

        toastMessage.observe(this, new Observer<Event<String>>() {
            @Override
            public void onChanged(@Nullable Event<String> event) {
                Log.d("cnrrr","changed " + "event");
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
//                if(req)
//                {
//                    Item item = new Item();
//                    videoListAdapter.addItem(item);
//                }
//                else
//                {
//                    videoListAdapter.removeItem();
//                }

            }
        });

        customerName.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String customerName) {
                Log.d("cnrr","cust name " + customerName);
                activityMainBinding.setName("Hi Rohit");
                activityMainBinding.setLabel("Welcome to TravelMate");
            }
        });



        if(locationLiveData.getValue()==null)
        {
            Log.d("cnrr","it is null");
            locationListViewModel.getLocationList("5c261ccb3000004f0067f6ec");
        }



//
//        LocationListViewModel locationListViewModel  = ViewModelProviders.of(this).get(LocationListViewModel.class);
//
//        locationListViewModel.getLocationList("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
