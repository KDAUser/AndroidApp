package com.example.androidapp.ui.searchProfile;

public class ProfileItem {
    private final int mSearchItemProfileId;
    private final String mSearchItemProfileName;

    public ProfileItem(int id, String Name) {
        mSearchItemProfileId = id;
        mSearchItemProfileName = Name;
    }

    public String getSearchItemProfileName() {
        return mSearchItemProfileName;
    }
    public int getSearchItemProfileId() { return mSearchItemProfileId; }
}
