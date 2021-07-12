package com.example.androidapp.ui.profile;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileViewModel extends ViewModel {

    private final JFIProfile loginProfile = new JFIProfile(0, "", "", "", "", null, new ArrayList<>());
    private final JFIProfile searchProfile = new JFIProfile(0, "", "", "", "", null, new ArrayList<>());
    private boolean showOwnProfile = true;

    private ProfileTrophiesAdapter mAdapter;

    public boolean isOwnProfileShow() { return showOwnProfile; }
    public void toggleShownProfile(){
        showOwnProfile = !showOwnProfile;
    }

    public JFIProfile getLoginProfile() { return loginProfile; }
    public JFIProfile getSearchProfile() { return searchProfile; }
    public JFIProfile getShownProfile(){
        if(showOwnProfile)
            return loginProfile;
        else
            return searchProfile;
    }

    public void getProfileFromDB(JSONObject user, JFIProfile profile){
        try {
            profile.setId(user.getInt("id"));
            profile.setLogin(user.getString("login"));
            profile.setEmail(user.getString("email"));
            profile.setDescription(user.getString("description"));
            profile.setRegisteredDate(user.getString("registered"));
            if(user.has("avatar")) {
                byte[] avatarBytes = Base64.decode(user.getString("avatar"), Base64.DEFAULT);
                profile.setAvatar(BitmapFactory.decodeByteArray(avatarBytes, 0, avatarBytes.length));
            } else {
                profile.setAvatar(null);
            }
            ArrayList<TrophyItem> trophiesList = new ArrayList<>();
            if(user.has("trophies")){
                JSONArray trophies = user.getJSONArray("trophies");
                for(int i=0; i<trophies.length(); i++) {
                    JSONObject trophy = trophies.getJSONObject(i);
                    trophiesList.add(new TrophyItem(trophy.getString("name"), trophy.getInt("stars")));
                }
            }
            profile.setTrophiesList(trophiesList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void buildRecyclerView(RecyclerView mTrophiesView, Context context, JFIProfile profile) {
        mTrophiesView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new ProfileTrophiesAdapter(profile.getTrophiesList());
        mTrophiesView.setLayoutManager(mLayoutManager);
        mTrophiesView.setAdapter(mAdapter);
    }

    public void updateTrophiesList(JFIProfile profile){
        mAdapter.filterList(profile.getTrophiesList());
    }

    public void clearViewModel(){
        loginProfile.clearJFIProfile();
        searchProfile.clearJFIProfile();
        showOwnProfile = true;
    }
}