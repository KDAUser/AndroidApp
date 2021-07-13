package com.example.androidapp.ui.locations;

import android.graphics.Bitmap;

public class TipItem {
    private final String mTipName;
    private final String mTipText;
    private Bitmap mTipImage;

    public TipItem(String mTipName, String mTipText, Bitmap mTipImage) {
        this.mTipName = mTipName;
        this.mTipText = mTipText;
        if(mTipImage != null) {
            this.mTipImage = mTipImage;
        }
    }

    public String getTipName() {
        return mTipName;
    }

    public String getTipText() {
        return mTipText;
    }

    public Bitmap getTipImage() {
        return mTipImage;
    }
}
