package com.example.androidapp.ui.locations;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.ui.searchLocation.LocationItem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationsViewModel extends ViewModel {

    private Boolean[] areStarsOn = new Boolean[] {true, true, true, true, true};
    private TipItem firstTip = new TipItem("Tip 1", "Go straight", null);
    private RecyclerView mTipsView;
    private RecyclerView.LayoutManager mLayoutManager;
    private LocationTipsAdapter mAdapter;
    private JFILocation mLocation = new JFILocation(0, false, "Example location", 5, null);

//    public void getLocationCoordinates() {
//        mLocation.setLatitude(52.2318);
//        mLocation.setLongitude(21.0060);
//    }

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

//    public void createExampleItemList(){
//        ArrayList<TipItem> tipsList = new ArrayList<>();
//        tipsList.add(new TipItem("Tip 2", "Turn left", null));
//        tipsList.add(new TipItem("Tip 3", "Turn right", null));
//        tipsList.add(new TipItem("Tip 4", "Turn back", null));
//        tipsList.add(new TipItem("Tip 5", "Look around", null));
//        mLocation.setLocationTips(tipsList);
//    }

    public void buildRecyclerView(RecyclerView mTipsView, Context context) {
        mTipsView = mTipsView;
        mTipsView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new LocationTipsAdapter(mLocation.getLocationTips());
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
        if(mLocation.getNumberOfStars()>1) {
            mLocation.setNumberOfStars(mLocation.getNumberOfStars()-1);
            updateStars(mLocation.getNumberOfStars());
            updateTipList();
        }
    }

    public void updateTipList(){
        ArrayList<TipItem> updateList = new ArrayList<>();
        int i = 4;
        updateList.add(firstTip);
        for (TipItem item : mLocation.getLocationTips()) {
            if (!areStarsOn[i]) {
                updateList.add(item);
            }
            i--;
        }
        mAdapter.filterList(updateList);
    }

    public Boolean[] getAreStarsOn() {
        return areStarsOn;
    }

    public Boolean checkLocation(Location location){
        double distance = Math.sqrt(Math.pow(mLocation.getLatitude()-location.getLatitude(),2)+Math.pow(mLocation.getLongitude()-location.getLongitude(),2))*111.139;
        if(distance<10) {
            return true;
        }
        return false;
    }

    public void setLocationId(int id) {
        mLocation.setLocationId(id);
    }

    public int getLocationId() {
        return mLocation.getLocationId();
    }

    public JFILocation getmLocation() {
        return mLocation;
    }

    public void getLocationFromDB(JSONObject location){
        try{
            ArrayList<TipItem> tipsList = new ArrayList<>();
            firstTip = new TipItem("Tip 2", location.getString("tip1"), null);
            tipsList.add(new TipItem("Tip 2", location.getString("tip2"), null));
            tipsList.add(new TipItem("Tip 2", location.getString("tip3"), null));
            tipsList.add(new TipItem("Tip 2", location.getString("tip4"), null));
            tipsList.add(new TipItem("Tip 2", location.getString("tip5"), null));
            mLocation = new JFILocation(location.getInt("id"), false, location.getString("name"), 5, tipsList);//location.getBoolean("isSolved") location.getInt("starsNumber")
            mLocation.setLatitude(location.getDouble("latitude"));
            mLocation.setLongitude(location.getDouble("longitude"));
            updateStars(mLocation.getNumberOfStars());
            updateTipList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<NameValuePair> prepareParams(Boolean isSolved, int numberOfStars) {
        List<NameValuePair> params = new ArrayList<>();
        if (isSolved != null) {
            params.add(new BasicNameValuePair("solved", String.valueOf(mLocation.isSolved())));
        }
        if (numberOfStars != 0) {
            params.add(new BasicNameValuePair("numberofstars", String.valueOf(mLocation.getNumberOfStars())));
        }
        return params;
    }

    public void setLocationSolved() {
        mLocation.setSolved(true);
    }

    public Boolean isLocationSolved() {
        return mLocation.isSolved();
    }
}
