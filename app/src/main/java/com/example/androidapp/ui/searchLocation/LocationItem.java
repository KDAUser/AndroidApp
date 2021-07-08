package com.example.androidapp.ui.searchLocation;

public class LocationItem {
    private int id;
    private String mSearchItemLocationName;

    public LocationItem(int id, String Name) {
        id = id;
        mSearchItemLocationName = Name;
    }

    public String getSearchItemLocationName() {
        return mSearchItemLocationName;
    }
}
