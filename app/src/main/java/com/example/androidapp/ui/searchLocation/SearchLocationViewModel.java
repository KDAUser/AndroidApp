package com.example.androidapp.ui.searchLocation;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchLocationViewModel extends ViewModel {

    private ArrayList<LocationItem> mLocationsList = new ArrayList<>();
    private ArrayList<LocationItem> filteredList;
    private SearchLocationAdapter mAdapter;
    private String filterText = "";

    public void filter(String text) {
        filteredList = new ArrayList<>();

        for (LocationItem item : mLocationsList) {
            if (item.getSearchItemLocationName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }

    public void createLocationsList(JSONArray locationsList) {
        mLocationsList = new ArrayList<>();
        try{
            for(int i=0; i<locationsList.length(); i++) {
                JSONObject location = locationsList.getJSONObject(i);
                mLocationsList.add(new LocationItem(location.getInt("id"), location.getString("name")));
            }
            mAdapter.filterList(mLocationsList);
            filteredList = mLocationsList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void buildRecyclerView(RecyclerView mLocationsView, Context context, SearchLocationAdapter.OnLocationListener onLocationListener) {
        mLocationsView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new SearchLocationAdapter(mLocationsList, onLocationListener);

        mLocationsView.setLayoutManager(mLayoutManager);
        mLocationsView.setAdapter(mAdapter);
    }

    public ArrayList<LocationItem> getFilteredList() {
        return filteredList;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }
    public String getFilterText() {
        return filterText;
    }
}