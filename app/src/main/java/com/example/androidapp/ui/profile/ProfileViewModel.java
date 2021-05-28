package com.example.androidapp.ui.profile;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {

    private boolean isUser;
    private MutableLiveData<String> mText;
    private ArrayList<TrophyItem> mTrophiesList;
    private RecyclerView mTrophiesView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProfileTrophiesAdapter mAdapter;

    public Boolean isUser() {
        return isUser;
    }

    public ProfileViewModel() {
        isUser = false;

        mText = new MutableLiveData<>();
        mText.setValue("Here is the profile description");
    }

    public void setUser() {
        isUser = true;
    }

    public void logout() {
        isUser = false;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void createExampleItemList(){
        mTrophiesList = new ArrayList<>();
        mTrophiesList.add(new TrophyItem("First location", 4));
        mTrophiesList.add(new TrophyItem("Second location", 2));
        mTrophiesList.add(new TrophyItem("Third location", 3));
        mTrophiesList.add(new TrophyItem("Fourth location", 5));
    }

    public void buildRecyclerView(RecyclerView mTrophiesView, Context context) {
        mTrophiesView = mTrophiesView;
        mTrophiesView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new ProfileTrophiesAdapter(mTrophiesList);
        mTrophiesView.setLayoutManager(mLayoutManager);
        mTrophiesView.setAdapter(mAdapter);
    }

    public void updateTrophiesList(){
        mAdapter.filterList(mTrophiesList);
    }
}