package com.example.androidapp.ui.searchLocation;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchLocationViewModel extends ViewModel {

    private ArrayList<LocationItem> mLocationsList;

    private RecyclerView mLocationsView;
    private SearchLocationAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public void filter(String text) {
        ArrayList<LocationItem> filteredList = new ArrayList<>();

        for (LocationItem item : mLocationsList) {
            if (item.getSearchItemLocationName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }

    public void createExampleLocationsList() {
        mLocationsList = new ArrayList<>();
        mLocationsList.add(new LocationItem("Białystok"));
        mLocationsList.add(new LocationItem("Bydgoszcz"));
        mLocationsList.add(new LocationItem("Gdańsk"));
        mLocationsList.add(new LocationItem("Gorzów Wielkopolski"));
        mLocationsList.add(new LocationItem("Katowice"));
        mLocationsList.add(new LocationItem("Kielce"));
        mLocationsList.add(new LocationItem("Kraków"));
        mLocationsList.add(new LocationItem("Lublin"));
        mLocationsList.add(new LocationItem("Łódź"));
    }

    public void buildRecyclerView(RecyclerView mLocationsView, Context context) {
        mLocationsView = mLocationsView;
        mLocationsView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new SearchLocationAdapter(mLocationsList);

        mLocationsView.setLayoutManager(mLayoutManager);
        mLocationsView.setAdapter(mAdapter);
    }
}