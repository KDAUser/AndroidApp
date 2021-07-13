package com.example.androidapp.ui.addLocations;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.lifecycle.ViewModel;

import com.example.androidapp.ui.locations.JFILocation;
import com.example.androidapp.ui.locations.TipItem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AddLocationsViewModel extends ViewModel {

    private final JFILocation mLocation = new JFILocation(0, false, "", 5, "", null);
    private double mLongitude = 0.0;
    private double mLatitude = 0.0;

    private void setLocationName(String locationName) {
        mLocation.setLocationName(locationName);
    }

    public void addTips(ArrayList<String> tipTexts, ArrayList<ImageView> tipImages) {
        String[] tipTextsTab = new String[5];
        Bitmap[] tipImagesTab = new Bitmap[5];
        ArrayList<TipItem> newTips = new ArrayList<>();
        int i = 0;
        for (ImageView tipImage : tipImages) {
            if (tipImage.getVisibility() == View.VISIBLE) {
                tipImagesTab[i] = ((BitmapDrawable) tipImage.getDrawable()).getBitmap();
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

    public JFILocation getLocation() {
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
            if (!locationTip.getTipText().equals("")) {
                i++;
            }
        }
        return (!mLocation.getLocationName().equals("")) & (i == 5) & (mLocation.getLatitude() != 0.0) & (mLocation.getLatitude() != 0.0);
    }

    public List<NameValuePair> prepareParams(Boolean[] areImagesSet){
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("name", mLocation.getLocationName()));
        params.add(new BasicNameValuePair("latitude", String.valueOf(mLocation.getLatitude())));
        params.add(new BasicNameValuePair("longitude", String.valueOf(mLocation.getLongitude())));
        int i = 0;
        for(TipItem tip: mLocation.getLocationTips()) {
            params.add(new BasicNameValuePair("tip"+(i+1), tip.getTipText()));
            if (areImagesSet[i]) {
                Bitmap bitmap = tip.getTipImage();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                byte[] byte_arr = stream.toByteArray();
                String encodedImage = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                params.add(new BasicNameValuePair("tip"+(i+1)+"image", encodedImage));
            }
            i++;
        }
        return params;
    }

    public void clearObject() {
        mLocation.clearJFILocation();
    }
}