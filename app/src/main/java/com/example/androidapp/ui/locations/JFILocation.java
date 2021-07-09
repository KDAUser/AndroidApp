package com.example.androidapp.ui.locations;

import java.util.ArrayList;

public class JFILocation {
    private int locationId;
    private boolean isSolved;
    private String locationName;
    private int numberOfStars;
    private ArrayList<TipItem> locationTips;
    private double longitude;
    private double latitude;
    private String updated;

    public JFILocation(int locationId, boolean isSolved, String locationName, int numberOfStars, String updated, ArrayList<TipItem> locationTips) {
        this.locationId = locationId;
        this.isSolved = isSolved;
        this.locationName = locationName;
        this.numberOfStars = numberOfStars;
        this.locationTips = locationTips;
        this.longitude = 0.0;
        this.latitude = 0.0;
        this.updated = updated;
    }

    public void clearJFILocation(){
        locationId = 0;
        isSolved = false;
        locationName = "";
        numberOfStars = 5;
        locationTips = null;
        longitude = 0.0;
        latitude = 0.0;
        updated = "";
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public ArrayList<TipItem> getLocationTips() {
        return locationTips;
    }

    public void setLocationTips(ArrayList<TipItem> locationTips) {
        this.locationTips = locationTips;
    }

    public int getNumberOfStars() {
        return numberOfStars;
    }

    public void setNumberOfStars(int stars) {
        this.numberOfStars = stars;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getUpdated() { return updated; }
}
