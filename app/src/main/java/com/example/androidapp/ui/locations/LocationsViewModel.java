package com.example.androidapp.ui.locations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocationsViewModel extends ViewModel {

    private final Boolean[] areStarsOn = new Boolean[] {true, true, true, true, true};
    private TipItem firstTip = new TipItem(R.string.first_tip, "", null);
    private LocationTipsAdapter mAdapter;
    private JFILocation mLocation = new JFILocation(0, false, "", 5, "", null);

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

    public void buildRecyclerView(RecyclerView mTipsView, Context context) {
        mTipsView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
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
        return distance < 10;
    }

    public int getLocationId() {
        return mLocation.getLocationId();
    }

    public JFILocation getLocation() {
        return mLocation;
    }

    public void getLocationFromDB(JSONObject location){
        try{
            ArrayList<TipItem> tipsList = new ArrayList<>();
            Bitmap[] imageList = new Bitmap[5];
            for (int i = 0; i < 5; i++) {
                if(location.has("tip"+(i+1)+"image")) {
                    byte[] imageBytes = Base64.decode(location.getString("tip" + (i + 1) + "image"), Base64.DEFAULT);
                    imageList[i] = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                } else {
                    imageList[i] = null;
                }
            }
            firstTip = new TipItem(R.string.first_tip, location.getString("tip1"), imageList[0]);
            tipsList.add(new TipItem(R.string.second_tip, location.getString("tip2"), imageList[1]));
            tipsList.add(new TipItem(R.string.third_tip, location.getString("tip3"), imageList[2]));
            tipsList.add(new TipItem(R.string.fourth_tip, location.getString("tip4"), imageList[3]));
            tipsList.add(new TipItem(R.string.fifth_tip, location.getString("tip5"), imageList[4]));
            boolean isSolved = false;
            if(location.getInt("isSolved") == 1)
                isSolved = true;

            mLocation = new JFILocation(location.getInt("id"),
                    isSolved,
                    location.getString("name"),
                    location.getInt("stars"),
                    location.getString("updated"),
                    tipsList);
            mLocation.setLatitude(location.getDouble("latitude"));
            mLocation.setLongitude(location.getDouble("longitude"));
            updateStars(mLocation.getNumberOfStars());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setLocationSolved() {
        mLocation.setSolved(true);
    }

    public Boolean isLocationSolved() {
        return mLocation.isSolved();
    }

    public int getLocationNumberOfStars(){return mLocation.getNumberOfStars();}

    public void clearViewModel(){mLocation.clearJFILocation();}
}
