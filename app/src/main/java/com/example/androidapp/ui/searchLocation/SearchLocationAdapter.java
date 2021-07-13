package com.example.androidapp.ui.searchLocation;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SearchLocationAdapter extends RecyclerView.Adapter<SearchLocationAdapter.LocationsViewHolder> {

    private ArrayList<LocationItem> mLocationsList;
    private final OnLocationListener mOnLocationListener;

    public static class LocationsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mSearchItemLocationName;
        OnLocationListener onLocationListener;

        public LocationsViewHolder(View itemView, OnLocationListener onLocationListener) {
            super(itemView);
            mSearchItemLocationName = itemView.findViewById(R.id.searchItemLocationName);
            this.onLocationListener = onLocationListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onLocationListener.onLocationClick(getAdapterPosition());
        }
    }

    public SearchLocationAdapter(ArrayList<LocationItem> locationsList, OnLocationListener onLocationListener) {
        mLocationsList = locationsList;
        mOnLocationListener = onLocationListener;
    }

    @NotNull
    @Override
    public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_search_item,
                parent, false);
        return new LocationsViewHolder(v, mOnLocationListener);
    }

    @Override
    public void onBindViewHolder(LocationsViewHolder holder, int position) {
        LocationItem currentItem = mLocationsList.get(position);

        holder.mSearchItemLocationName.setText(currentItem.getSearchItemLocationName());
    }

    @Override
    public int getItemCount() {
        return mLocationsList.size();
    }

    public void filterList(ArrayList<LocationItem> filteredList) {
        mLocationsList = filteredList;
        notifyDataSetChanged();
    }

    public interface OnLocationListener {
        void onLocationClick(int position);
    }
}
