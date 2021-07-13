package com.example.androidapp.ui.searchLocation;

public class LocationItem {
    private final int id;
    private final String mSearchItemLocationName;

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
