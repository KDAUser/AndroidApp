package com.example.androidapp.ui.searchLocation;

public class LocationItem {
    private int id;
    private String mSearchItemLocationName;

    public LocationItem(int id, String Name) {
        this.id = id;
        mSearchItemLocationName = Name;
    }

    public String getSearchItemLocationName() {
        return mSearchItemLocationName;
    }

    public int getId() {
        return id;
    }
}
