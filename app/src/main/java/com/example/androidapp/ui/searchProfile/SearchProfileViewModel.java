package com.example.androidapp.ui.searchProfile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SearchProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is search profile fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}