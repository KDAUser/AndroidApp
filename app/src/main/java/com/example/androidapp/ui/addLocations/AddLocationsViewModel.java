package com.example.androidapp.ui.addLocations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddLocationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddLocationsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is add locations fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
