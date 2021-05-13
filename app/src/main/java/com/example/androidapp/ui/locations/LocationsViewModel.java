package com.example.androidapp.ui.locations;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationsViewModel extends ViewModel {

    private Boolean[] areStarsOn = new Boolean[] {true, true, true, true, true};
    private ArrayList<TipItem> mTipsList;
    private TipItem firstTip = new TipItem("Tip 1", "Go straight", null);
    private RecyclerView mTipsView;
    private RecyclerView.LayoutManager mLayoutManager;
    private LocationTipsAdapter mAdapter;
    private int stars = 5;

    public void setStars (Boolean[] starStatus, ArrayList<ImageView> starsON, ArrayList<ImageView> starsOFF) {
        int i = 0;

        for(ImageView starOn: starsON){
            if (starStatus[i]){
                starOn.setVisibility(View.VISIBLE);
            } else {
                starOn.setVisibility(View.GONE);
            }
            i++;
        }
        i = 0;
        for(ImageView starOff: starsOFF) {
            if (starStatus[i]) {
                starOff.setVisibility(View.GONE);
            } else {
                starOff.setVisibility(View.VISIBLE);
            }
            i++;
        }
    }

    public void createExampleItemList(){
        mTipsList = new ArrayList<>();
        mTipsList.add(new TipItem("Tip 2", "Turn left", null));
        mTipsList.add(new TipItem("Tip 3", "Turn right", null));
        mTipsList.add(new TipItem("Tip 4", "Turn back", null));
        mTipsList.add(new TipItem("Tip 5", "Look around", null));
    }

    public void buildRecyclerView(RecyclerView mTipsView, Context context) {
        mTipsView = mTipsView;
        mTipsView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new LocationTipsAdapter(mTipsList);
        mTipsView.setLayoutManager(mLayoutManager);
        mTipsView.setAdapter(mAdapter);
    }

    private void updateStars(int numberOfStars){
        switch (numberOfStars){
            case 5:
                areStarsOn[0] = true;
                areStarsOn[1] = true;
                areStarsOn[2] = true;
                areStarsOn[3] = true;
                areStarsOn[4] = true;
                break;
            case 4:
                areStarsOn[0] = true;
                areStarsOn[1] = true;
                areStarsOn[2] = true;
                areStarsOn[3] = true;
                areStarsOn[4] = false;
                break;
            case 3:
                areStarsOn[0] = true;
                areStarsOn[1] = true;
                areStarsOn[2] = true;
                areStarsOn[3] = false;
                areStarsOn[4] = false;
                break;
            case 2:
                areStarsOn[0] = true;
                areStarsOn[1] = true;
                areStarsOn[2] = false;
                areStarsOn[3] = false;
                areStarsOn[4] = false;
                break;
            case 1:
                areStarsOn[0] = true;
                areStarsOn[1] = false;
                areStarsOn[2] = false;
                areStarsOn[3] = false;
                areStarsOn[4] = false;
                break;
        }
    }

    public void addTip(){
        ArrayList<TipItem> updateList = new ArrayList<>();
        int i = 4;
        if(stars>1) {
            stars--;
            updateStars(stars);
            updateList.add(firstTip);
            for (TipItem item : mTipsList) {
                if (!areStarsOn[i]) {
                    updateList.add(item);
                }
                i--;
            }
            mAdapter.filterList(updateList);
        }
    }

    public void firstTip(){
        ArrayList<TipItem> firstList = new ArrayList<>();
        firstList.add(firstTip);
        mAdapter.filterList(firstList);
    }

    public Boolean[] getAreStarsOn() {
        return areStarsOn;
    }
}
