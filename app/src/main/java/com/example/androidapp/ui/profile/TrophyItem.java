package com.example.androidapp.ui.profile;

public class TrophyItem {
    private final String mLocationName;
    private final int mNumberOfStars;
    private final String mTrophyDate;

    public TrophyItem(String mLocationName, int mNumberOfStars, String mTrophyDate) {
        this.mLocationName = mLocationName;
        this.mNumberOfStars = mNumberOfStars;
        this.mTrophyDate = mTrophyDate;
    }

    public String getLocationName() {
        return mLocationName;
    }

    public int getNumberOfStars() {
        return mNumberOfStars;
    }

    public String getTrophyDate() { return mTrophyDate; }
}
