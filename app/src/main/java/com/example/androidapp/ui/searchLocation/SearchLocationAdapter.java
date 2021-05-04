package com.example.androidapp.ui.searchLocation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import java.util.ArrayList;

public class SearchLocationAdapter extends RecyclerView.Adapter<SearchLocationAdapter.LocationsViewHolder> {

    private ArrayList<LocationItem> mLocationsList;

    public static class LocationsViewHolder extends RecyclerView.ViewHolder {
        public TextView mSearchItemLocationName;

        public LocationsViewHolder(View itemView) {
            super(itemView);
            mSearchItemLocationName = itemView.findViewById(R.id.searchItemLocationName);
        }
    }

    public SearchLocationAdapter(ArrayList<LocationItem> locationsList) {
        mLocationsList = locationsList;
    }

    @Override
    public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_search_item,
                parent, false);
        LocationsViewHolder evh = new LocationsViewHolder(v);
        return evh;
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
}
