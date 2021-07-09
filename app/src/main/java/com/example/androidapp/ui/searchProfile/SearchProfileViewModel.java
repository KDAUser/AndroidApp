package com.example.androidapp.ui.searchProfile;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchProfileViewModel extends ViewModel {

    private ArrayList<ProfileItem> mProfilesList = new ArrayList<>();
    private ArrayList<ProfileItem> filteredList;

    private RecyclerView mProfilesView;
    private SearchProfileAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private String filterText = "";

    public void filter(String text) {
        filteredList = new ArrayList<>();

        for (ProfileItem item : mProfilesList) {
            if (item.getSearchItemProfileName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }

    public void createProfilesList(JSONArray usersList) {
        mProfilesList = new ArrayList<>();
        try{
            for(int i=0; i<usersList.length(); i++) {
                JSONObject user = usersList.getJSONObject(i);
                mProfilesList.add(new ProfileItem(user.getInt("id"), user.getString("login")));
            }
            mAdapter.filterList(mProfilesList);
            filteredList = mProfilesList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void buildRecyclerView(RecyclerView mProfilesView, Context context, SearchProfileAdapter.OnProfileListener onProfileListener) {
        mProfilesView = mProfilesView;
        mProfilesView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new SearchProfileAdapter(mProfilesList, onProfileListener);

        mProfilesView.setLayoutManager(mLayoutManager);
        mProfilesView.setAdapter(mAdapter);
    }

    public ArrayList<ProfileItem> getFilteredList() {
        return filteredList;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }
    public String getFilterText() {
        return filterText;
    }
}