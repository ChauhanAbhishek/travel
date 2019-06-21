package com.example.rup.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rup.LocationOnClickListener;
import com.example.rup.R;
import com.example.rup.databinding.LocationRowItemBinding;
import com.example.rup.models.Location;
import com.example.rup.viewmodels.LocationListViewModel;

import java.util.ArrayList;
import java.util.List;

public class LocationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Location> locations = new ArrayList<>();
    private Context context;
    private LocationListViewModel viewModel;
    LocationOnClickListener locationOnClickListener;
    private static int NORMAL=1;

    public LocationListAdapter(LocationListViewModel viewModel, Context context, LocationOnClickListener locationOnClickListener) {
        this.viewModel = viewModel;
        this.context = context;
        this.locationOnClickListener = locationOnClickListener;
    }

    public void updateList(List<Location> locations) {
        this.locations.clear();
        this.locations.addAll(locations);
        Log.d("cnrr " , "update " + this.locations.size());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==NORMAL)
        {
             LocationRowItemBinding locationRowItemBinding = DataBindingUtil
                    .inflate(LayoutInflater.from(parent.getContext()), R.layout.location_row_item,
                            parent, false);
            return new LocationItemViewHolder(locationRowItemBinding);
        }

        return null;


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof LocationItemViewHolder) {
            ((LocationItemViewHolder) holder).getLocationRowItemBinding().setLocation(locations.get(position));
            ((LocationItemViewHolder) holder).getLocationRowItemBinding().locationImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   locationOnClickListener.openLocation(locations.get(holder.getAdapterPosition()));
                }
            });
        }

    }

    @Override
    public int getItemCount() {

        return locations.size();
    }


    public class LocationItemViewHolder extends RecyclerView.ViewHolder {

        LocationRowItemBinding locaitonRowItemBinding;

        LocationItemViewHolder(@NonNull LocationRowItemBinding locaitonRowItemBinding) {
            super(locaitonRowItemBinding.getRoot());
            this.locaitonRowItemBinding = locaitonRowItemBinding;
        }

        public LocationRowItemBinding getLocationRowItemBinding() {
            return locaitonRowItemBinding;
        }

    }

    @Override
    public int getItemViewType(int position) {

        return NORMAL;
    }
}
