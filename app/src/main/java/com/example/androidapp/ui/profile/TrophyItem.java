package com.example.androidapp.ui.profile;

public class TrophyItem {
    private String mLocationName;
    private int mNumberOfStars;

    public TrophyItem(String mLocationName, int mNumberOfStars) {
        this.mLocationName = mLocationName;
        this.mNumberOfStars = mNumberOfStars;
    }

    public String getmLocationName() {
        return mLocationName;
    }

    public int getmNumberOfStars() {
        return mNumberOfStars;
    }
}
