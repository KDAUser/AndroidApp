package com.example.androidapp.ui.profile;

import android.content.Context;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {

//    private boolean isUser;
    private String profileDescription;
    private ImageView Avatar;
    private ArrayList<TrophyItem> mTrophiesList;
    private RecyclerView mTrophiesView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProfileTrophiesAdapter mAdapter;
    private int userId;

//    public Boolean isUser() {
//        return isUser;
//    }
//
//    public ProfileViewModel() {
//        isUser = false;
//    }
//
//    public void setUser() {
//        isUser = true;
//    }
//    public void logout() {
//        isUser = false;
//    }

    public ImageView getAvatar() {
        return Avatar;
    }

    private void setProfileDescription() {
        //TODO: pobranie opisu użytkownika po userId
        profileDescription = "Here is the profile description";
    }

    private void setAvatar() {
        //TODO: pobranie zdjęcia awataru dla danego userId
        Avatar = null;
    }

    private void setmTrophiesList() {
        //TODO: pobranie nazw rozwiązanych lokalizacji i ich ilości gwiazdek dla danego userId
        mTrophiesList = new ArrayList<>();
        mTrophiesList.add(new TrophyItem("First location", 4));
        mTrophiesList.add(new TrophyItem("Second location", 2));
        mTrophiesList.add(new TrophyItem("Third location", 3));
        mTrophiesList.add(new TrophyItem("Fourth location", 5));
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setView() {
        setmTrophiesList();
        //setProfileDescription();
        //setAvatar();
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