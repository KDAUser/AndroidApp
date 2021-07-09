package com.example.androidapp.ui.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {

    private int id;
    private String login;
    private String email;
    private String description;
    private Bitmap avatar;

    private RecyclerView mTrophiesView;
    private ProfileTrophiesAdapter mAdapter;
    private ArrayList<TrophyItem> mTrophiesList;
    private RecyclerView.LayoutManager mLayoutManager;

    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getEmail() { return email; }
    public String getDescription() { return description; }
    public Bitmap getAvatar() { return avatar; }

    public void getProfileFromDB(JSONObject user){
        try {
            id = user.getInt("id");
            login = user.getString("login");
            email = user.getString("email");
            description = user.getString("description");
            byte[] avatarBytes = Base64.decode(user.getString("avatar"), Base64.DEFAULT);
            avatar = BitmapFactory.decodeByteArray(avatarBytes, 0, avatarBytes.length);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setmTrophiesList() {
        //TODO: pobranie nazw rozwiązanych lokalizacji i ich ilości gwiazdek dla danego userId
        mTrophiesList = new ArrayList<>();
        mTrophiesList.add(new TrophyItem("First location", 4));
        mTrophiesList.add(new TrophyItem("Second location", 2));
        mTrophiesList.add(new TrophyItem("Third location", 3));
        mTrophiesList.add(new TrophyItem("Fourth location", 5));
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