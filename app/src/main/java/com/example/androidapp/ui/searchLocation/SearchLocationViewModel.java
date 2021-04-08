package com.example.androidapp.ui.searchLocation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchLocationViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchLocationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is search location fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}