package com.example.androidapp.ui.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.ui.searchProfile.ProfileItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {

    private int id;
    private String login;
    private String email;
    private String description;
    private Bitmap avatar;
    private String registeredDate;

    private RecyclerView mTrophiesView;
    private ProfileTrophiesAdapter mAdapter;
    private ArrayList<TrophyItem> mTrophiesList;
    private RecyclerView.LayoutManager mLayoutManager;

    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getEmail() { return email; }
    public String getDescription() { return description; }
    public Bitmap getAvatar() { return avatar; }
    public String getRegisteredDate() { return registeredDate; }

    public void getProfileFromDB(JSONObject user, JSONArray trophiesList){
        try {
            id = user.getInt("id");
            login = user.getString("login");
            email = user.getString("email");
            description = user.getString("description");
            registeredDate = user.getString("registered");
            if(user.has("avatar")) {
                byte[] avatarBytes = Base64.decode(user.getString("avatar"), Base64.DEFAULT);
                avatar = BitmapFactory.decodeByteArray(avatarBytes, 0, avatarBytes.length);
            } else {
                avatar = null;
            }
            for(int i=0; i<trophiesList.length(); i++) {
                JSONObject trophy = trophiesList.getJSONObject(i);
                mTrophiesList.add(new TrophyItem(trophy.getString("name"), trophy.getInt("stars")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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