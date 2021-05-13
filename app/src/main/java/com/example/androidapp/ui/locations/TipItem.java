package com.example.androidapp.ui.locations;

import android.widget.ImageView;

public class TipItem {
    private String mTipName;
    private String mTipText;
    private ImageView mTipImage;

    public TipItem(String mTipName, String mTipText, ImageView mTipImage) {
        this.mTipName = mTipName;
        this.mTipText = mTipText;
        if(mTipImage != null) {
            this.mTipImage = mTipImage;
        }
    }

    public String getmTipName() {
        return mTipName;
    }

    public String getmTipText() {
        return mTipText;
    }

    public ImageView getmTipImage() {
        return mTipImage;
    }
}
