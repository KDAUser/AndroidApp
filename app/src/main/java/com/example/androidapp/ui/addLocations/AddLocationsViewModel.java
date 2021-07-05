package com.example.androidapp.ui.addLocations;

import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidapp.ui.locations.JFILocation;
import com.example.androidapp.ui.locations.TipItem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class AddLocationsViewModel extends ViewModel {

    private JFILocation mLocation = new JFILocation(0, false, "", 5, null);
    private double mLongitude = 0.0;
    private double mLatitude = 0.0;

    private void setLocationName(String locationName) {
        mLocation.setLocationName(locationName);
    }

    private void addTips(ArrayList<String> tipTexts, ArrayList<ImageView> tipImages) {
        String[] tipTextsTab = new String[5];
        ImageView[] tipImagesTab = new ImageView[5];
        ArrayList<TipItem> newTips = new ArrayList<>();
        int i = 0;
        for (ImageView tipImage : tipImages) {
            if (tipImage.getVisibility() == View.VISIBLE) {
                tipImagesTab[i] = tipImage;
            }
            i++;
        }
        i = 0;
        for (String tipText : tipTexts) {
            tipTextsTab[i] = tipText;
            i++;
        }
        newTips.add(new TipItem("First Tip", tipTextsTab[0], tipImagesTab[0]));
        newTips.add(new TipItem("Second Tip", tipTextsTab[1], tipImagesTab[1]));
        newTips.add(new TipItem("Third Tip", tipTextsTab[2], tipImagesTab[2]));
        newTips.add(new TipItem("Fourth Tip", tipTextsTab[3], tipImagesTab[3]));
        newTips.add(new TipItem("Fifth Tip", tipTextsTab[4], tipImagesTab[4]));
        mLocation.setLocationTips(newTips);
    }

    /*private void addTips(ArrayList<String> tipTexts, ArrayList<ImageView> tipImages) {
        ArrayList<TipItem> newTips = new ArrayList<>();
        String[] tipNames = {"First Tip", "Second Tip", "Third Tip", "Fourth Tip", "Fifth Tip"};
        int i = 0;
        for (String tipText: tipTexts; ImageView tipImage: tipImages) {
            newTips.add(new TipItem(tipNames[i], tipText, tipImage));
            i++;
        }
        mLocation.setLocationTips(newTips);
    }*/

    public JFILocation getmLocation() {
        return mLocation;
    }

    private void setCoordinates() {
        mLocation.setLongitude(mLongitude);
        mLocation.setLatitude(mLatitude);
    }

    public void saveWrittenValues(double longitude, double latitude) {
        mLongitude = longitude;
        mLatitude = latitude;
    }

    public void saveActualValues(Location location) {
        mLongitude = location.getLongitude();
        mLatitude = location.getLatitude();
    }

    public void saveLocation(String locationName, ArrayList<String> tipTexts, ArrayList<ImageView> tipImages) {
        setLocationName(locationName);
        addTips(tipTexts, tipImages);
        setCoordinates();
    }

    public Boolean isLocationComplete() {
        int i = 0;
        for (TipItem locationTip: mLocation.getLocationTips()) {
            if (!locationTip.getmTipText().equals("")) {
                i++;
            }
        }
        if ((!mLocation.getLocationName().equals(""))&(i==5)&(mLocation.getLatitude()!=0.0)&(mLocation.getLatitude()!=0.0)) {
            return true;
        }
        return false;
    }

    public List<NameValuePair> uploadLocation(){
        List<NameValuePair>  params = new ArrayList<>();
        //params.add(new BasicNameValuePair("pass", currPasswordStr));
        return params;
    }
}