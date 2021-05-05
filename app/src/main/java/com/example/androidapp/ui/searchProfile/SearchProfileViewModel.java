package com.example.androidapp.ui.searchProfile;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchProfileViewModel extends ViewModel {

    private ArrayList<ProfileItem> mProfilesList;

    private RecyclerView mProfilesView;
    private SearchProfileAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public void filter(String text) {
        ArrayList<ProfileItem> filteredList = new ArrayList<>();

        for (ProfileItem item : mProfilesList) {
            if (item.getSearchItemProfileName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }

    public void createExampleProfilesList() {
        mProfilesList = new ArrayList<>();
        mProfilesList.add(new ProfileItem("Jake"));
        mProfilesList.add(new ProfileItem("Emily"));
        mProfilesList.add(new ProfileItem("Harry"));
        mProfilesList.add(new ProfileItem("Jacob"));
        mProfilesList.add(new ProfileItem("Linda"));
        mProfilesList.add(new ProfileItem("James"));
        mProfilesList.add(new ProfileItem("William"));
        mProfilesList.add(new ProfileItem("Conor"));
        mProfilesList.add(new ProfileItem("Susan"));
    }

    public void buildRecyclerView(RecyclerView mProfilesView, Context context) {
        mProfilesView = mProfilesView;
        mProfilesView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new SearchProfileAdapter(mProfilesList);

        mProfilesView.setLayoutManager(mLayoutManager);
        mProfilesView.setAdapter(mAdapter);
    }
}