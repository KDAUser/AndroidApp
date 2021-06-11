package com.example.androidapp.ui.locations;

import java.util.ArrayList;

public class JFILocation {
    private int locationId;
    private boolean isSolved;
    private String locationName;
    private int numberOfStars;
    private ArrayList<TipItem> locationTips;

    public JFILocation(int locationId, boolean isSolved, String locationName, int numberOfStars, ArrayList<TipItem> locationTips) {
        this.locationId = locationId;
        this.isSolved = isSolved;
        this.locationName = locationName;
        this.numberOfStars = numberOfStars;
        this.locationTips = locationTips;
    }

    public String getLocationName() {
        return locationName;
    }

    public ArrayList<TipItem> getLocationTips() {
        return locationTips;
    }

    public int getNumberOfStars() {
        return numberOfStars;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setLocationTips(ArrayList<TipItem> locationTips) {
        this.locationTips = locationTips;
    }

    public void setNumberOfStars(int stars) {
        this.numberOfStars = stars;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}
