package com.example.androidapp.ui.searchLocation;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchLocationViewModel extends ViewModel {

    private ArrayList<LocationItem> mLocationsList;
    private ArrayList<LocationItem> filteredList;

    private RecyclerView mLocationsView;
    private SearchLocationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String filterText;

    public void filter(String text) {
        filteredList = new ArrayList<>();

        for (LocationItem item : mLocationsList) {
            if (item.getSearchItemLocationName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }

    public void createExampleLocationsList() {
        mLocationsList = new ArrayList<>();
//        mLocationsList.add(new LocationItem(1, "Białystok"));
//        mLocationsList.add(new LocationItem(2, "Bydgoszcz"));
//        mLocationsList.add(new LocationItem(3, "Gdańsk"));
//        mLocationsList.add(new LocationItem(4, "Gorzów Wielkopolski"));
//        mLocationsList.add(new LocationItem(5, "Katowice"));
//        mLocationsList.add(new LocationItem(6, "Kielce"));
//        mLocationsList.add(new LocationItem(7, "Kraków"));
//        mLocationsList.add(new LocationItem(8, "Lublin"));
//        mLocationsList.add(new LocationItem(9, "Łódź"));
    }

    public void createLocationsList(JSONArray locationsList) {
        mLocationsList = new ArrayList<>();
        try{
            for(int i=0; i<locationsList.length(); i++) {
                JSONObject location = locationsList.getJSONObject(i);
                mLocationsList.add(new LocationItem(location.getInt("id"), location.getString("name")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void buildRecyclerView(RecyclerView mLocationsView, Context context, SearchLocationAdapter.OnLocationListener onLocationListener) {
        mLocationsView = mLocationsView;
        mLocationsView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
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