package com.example.androidapp.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private boolean isUser;
    private MutableLiveData<String> mText;

    public boolean isUser() {
        return isUser;
    }

    public ProfileViewModel() {
        isUser = false;

        mText = new MutableLiveData<>();
        mText.setValue("This is profile fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}